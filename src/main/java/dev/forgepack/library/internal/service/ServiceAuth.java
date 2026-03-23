package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.internal.configuration.ConfigurationJWT;
import dev.forgepack.library.internal.model.Token;
import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.internal.payload.DTORequestToken;
import dev.forgepack.library.internal.payload.DTORequestUser;
import dev.forgepack.library.internal.payload.DTOResponseUser;
import dev.forgepack.library.internal.payload.DTORequestUserAuth;
import dev.forgepack.library.internal.payload.DTOResponseToken;
import dev.forgepack.library.internal.repository.RepositoryToken;
import dev.forgepack.library.internal.repository.RepositoryUser;
import dev.forgepack.library.internal.utils.Information;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceAuth {

//    private final ServiceTOTP serviceTOTP;
//    private final ServiceEmail serviceEmail;
//    private final ServiceRecaptcha serviceRecaptcha;
//    private final E2EE e2EE;
    private final AuthenticationManager authenticationManager;
    private final ConfigurationJWT configurationJwt;
    private final RepositoryToken repositoryToken;
    private final RepositoryUser repositoryUser;
    private final Mapper<Token, DTORequestToken, DTOResponseToken> mapper;
    private final ServiceCustomUserDetails serviceCustomUserDetails;
    private final ServiceUser serviceUser;
    private static final Logger log = LoggerFactory.getLogger(Information.class);

    public ServiceAuth(AuthenticationManager authenticationManager, ConfigurationJWT configurationJwt, RepositoryToken repositoryToken, RepositoryUser repositoryUser, Mapper<Token, DTORequestToken, DTOResponseToken> mapper, ServiceCustomUserDetails serviceCustomUserDetails, ServiceUser serviceUser) {
        this.authenticationManager = authenticationManager;
        this.configurationJwt = configurationJwt;
        this.repositoryToken = repositoryToken;
        this.repositoryUser = repositoryUser;
        this.mapper = mapper;
        this.serviceCustomUserDetails = serviceCustomUserDetails;
        this.serviceUser = serviceUser;
    }

    public DTOResponseToken login(DTORequestUserAuth dtoRequestUserAuth) {
//        captchaTest(dtoRequestUserAuth.getCaptchaToken());
        serviceCustomUserDetails.loadUserByUsername(dtoRequestUserAuth.username());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dtoRequestUserAuth.username(), dtoRequestUserAuth.password()));
//        serviceTOTP.validateTOTP(dtoRequestUserAuth.username(), dtoRequestUserAuth.totpKey());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = configurationJwt.generateToken(authentication.getName());
        UUID refreshToken = UUID.randomUUID();
        repositoryToken.save(new Token(refreshToken, true));
        return new DTOResponseToken(
                token,
                refreshToken,
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet())
        );
    }

    public DTOResponseToken refresh(DTORequestToken dtoRequestToken) {
        if (repositoryToken.existsByRefreshToken(dtoRequestToken.refreshToken()) &&
                configurationJwt.validateJWT(dtoRequestToken.accessToken())) {
            UserDetails userDetails = serviceCustomUserDetails.loadUserByUsername(
                    configurationJwt.getUsernameFromJWT(dtoRequestToken.accessToken())
            );
            String tokenResponse = configurationJwt.generateToken(configurationJwt.getUsernameFromJWT(dtoRequestToken.accessToken()));
            Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
            return new DTOResponseToken(tokenResponse, dtoRequestToken.refreshToken(), roles);
        } else {
            logout(dtoRequestToken.refreshToken());
            return null;
        }
    }

    public DTOResponseToken logout(UUID refreshToken) {
        return repositoryToken.findByRefreshToken(refreshToken)
                .map(token -> {
                    repositoryToken.deleteById(token.getId());
                    return mapper.toResponse(token);
                })
                .orElseThrow(() ->
                        new RuntimeException("Token not found.")
                );
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
    public void validUser(String username) {
        User entity = repositoryUser.findByUsername(username).orElseThrow(() -> new RuntimeException("Resource not found"));
        if(!entity.getActive()) throw new RuntimeException("User blocked");
    }
    public void resetPassword(String username/*, String captchaToken*/) {
//        captchaTest(captchaToken);
        User entity = repositoryUser.findByUsername(username).orElseThrow(() -> new RuntimeException("Resource not found"));
//        serviceEmail.sendSimpleMessage(entity.getEmail(), "Recovery password", entity.getPassword());
    }
//    public void resetTotp(String username/*, String captchaToken*/) throws Exception {
////        captchaTest(captchaToken);
//        User entity = repositoryUser.findByUsername(username).orElseThrow(() -> new RuntimeException("Resource not found"));
//        serviceEmail.sendSimpleMessage(entity.getEmail(), "Recovery totp", e2EE.decrypt(entity.getSecret()));
//    }
//    public void captchaTest(String captchaToken) {
//        if (!serviceRecaptcha.validateCaptcha(captchaToken)) {
//            log.error("Invalid or suspicious CAPTCHA: {}", captchaToken);
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or suspicious CAPTCHA");
//        }
//    }
}
