package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.api.service.ServiceInterfaceEmail;
import dev.forgepack.library.api.service.ServiceInterfaceSecret;
import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.internal.payload.DTORequestUser;
import dev.forgepack.library.internal.payload.DTOResponseUser;
import dev.forgepack.library.internal.payload.DTORequestUserAuth;
import dev.forgepack.library.internal.repository.RepositoryUser;
import dev.forgepack.library.internal.utils.E2EE;
import dev.forgepack.library.internal.utils.Information;
import dev.forgepack.library.internal.utils.QRCode;
import org.apache.commons.codec.binary.Base32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class ServiceSecret implements ServiceInterfaceSecret {

    private final ServiceInterfaceEmail serviceEmail;
    private final ServiceSecret serviceSecret;
//    private final ServiceRecaptcha serviceRecaptcha;
    private final E2EE e2EE;
    private final RepositoryUser repositoryUser;
    private final Mapper<User, DTORequestUser, DTOResponseUser> mapper;
    private final ServiceUser serviceUser;
    private static final Logger log = LoggerFactory.getLogger(Information.class);

    public ServiceSecret(ServiceInterfaceEmail serviceEmail, ServiceSecret serviceSecret, E2EE e2EE, RepositoryUser repositoryUser, Mapper<User, DTORequestUser, DTOResponseUser> mapper, ServicePassword servicePassword, ServiceUser serviceUser) {
        this.serviceEmail = serviceEmail;
        this.serviceSecret = serviceSecret;
        this.e2EE = e2EE;
        this.repositoryUser = repositoryUser;
        this.mapper = mapper;
        this.serviceUser = serviceUser;
    }
    @Override
    public DTOResponseUser resetSecret(String username) {
        User user = serviceUser.isValidToChange(username);
        String secret = serviceSecret.generateSecret();
        try {
            user.setSecret(secret);
            repositoryUser.save(user);
            byte[] qrCodeBytes = QRCode.generateQRCodeBytes(buildTotpUri(user.getUsername(), user.getSecret()), 200);
            String emailContent = serviceEmail.buildWelcomeEmailContent(user.getUsername(), "Your password is the same as before", secret);
            serviceEmail.sendHtmlMessageWithAttachment(user.getEmail(), "Reset TOTP requested", emailContent, qrCodeBytes, "qrcode.png", "image/png");
            log.info("{} resetting user totp with ID: {}", new Information().getCurrentUser().orElse("Unknown User"), user.getId());
            return mapper.toResponse(user);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to reset TOTP for user: " + user.getUsername());
        }
    }
    public void addAttempt(DTORequestUserAuth dtoRequestUserAuth) {
        User entity = repositoryUser.findByUsername(dtoRequestUserAuth.username()).orElseThrow(() -> new RuntimeException("Resource not found"));
        entity.setAttempt(entity.getAttempt() == null ? 0 : entity.getAttempt() + 1);
        if(entity.getAttempt() > 4) {
            entity.setActive(false);
            repositoryUser.save(entity);
            throw new RuntimeException("User blocked");
        }
        repositoryUser.save(entity);
    }
    public void resetAttempts(String username) {
        repositoryUser.findByUsername(username).ifPresent(user -> {
            user.setAttempt(0);
            repositoryUser.save(user);
        });
    }
//    @Override
//    public void captchaTest(String captchaToken) {
//        if (!serviceRecaptcha.validateCaptcha(captchaToken)) {
//            log.error("Invalid or suspicious CAPTCHA: {}", captchaToken);
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or suspicious CAPTCHA");
//        }
//    }
    public void validateTOTP(String userName, Integer totpKey) {
        log.info("Validating TOTP for user: {}", userName);
        User user = repositoryUser.findByUsername(userName).orElseThrow(() -> new BadCredentialsException("User not found"));
        String secret = user.getSecret();
        try {
            secret = e2EE.decrypt(secret);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid secret");
        }
        if (StringUtils.hasText(secret)) {
            if (totpKey != null) {
                try {
                    if (!verifyCode(secret, totpKey, 1)) {
                        log.info("TOTP code {} was not valid for user {}", totpKey, userName);
                        throw new BadCredentialsException("Invalid TOTP code");
                    }
                } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                    throw new InternalAuthenticationServiceException("Failed to verify TOTP code due to cryptographic error", e);
                }
            } else {
//                throw new MissingTOTPKeyAuthenticatorException("TOTP code is mandatory");
                throw new BadCredentialsException("TOTP code is mandatory");
            }
        }
    }
    public String generateSecret() {
        byte[] buffer = new byte[10];
        new SecureRandom().nextBytes(buffer);
        return new String(new Base32().encode(buffer));
    }
    public boolean verifyCode(String secret, int code, int variance)
            throws InvalidKeyException, NoSuchAlgorithmException {
        long timeIndex = System.currentTimeMillis() / 1000 / 30;
        byte[] secretBytes = new Base32().decode(secret);
        for (int i = -variance; i <= variance; i++) {
            long calculatedCode = getCode(secretBytes, timeIndex + i);
            if (calculatedCode == code) {
                return true;
            }
        }
        return false;
    }
    private long getCode(byte[] secret, long timeIndex) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(secret, "HmacSHA1"));
        ByteBuffer buffer = ByteBuffer.allocate(8).putLong(timeIndex);
        byte[] hash = mac.doFinal(buffer.array());
        int offset = hash[19] & 0xf;
        long truncatedHash = hash[offset] & 0x7f;
        for (int i = 1; i < 4; i++) {
            truncatedHash <<= 8;
            truncatedHash |= hash[offset + i] & 0xff;
        }
        return truncatedHash % 1000000;
    }
    public String buildTotpUri(String username, String secret) throws Exception {
        return String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                username,
                username + "@forgepack.com",
                e2EE.decrypt(secret),
                "Forgepack"
        );
    }

}
