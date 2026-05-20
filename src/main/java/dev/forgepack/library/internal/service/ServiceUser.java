package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.api.repository.RepositoryGeneric;
import dev.forgepack.library.api.service.ServiceEmail;
import dev.forgepack.library.api.validator.UniqueCheckable;
import dev.forgepack.library.api.service.ServiceGeneric;
import dev.forgepack.library.internal.model.Role;
import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.internal.payload.DTORequestUser;
import dev.forgepack.library.internal.payload.DTORequestUserAuth;
import dev.forgepack.library.internal.payload.DTOResponseUser;
import dev.forgepack.library.internal.repository.RepositoryRole;
import dev.forgepack.library.internal.repository.RepositoryUser;
import dev.forgepack.library.internal.utils.E2EE;
import dev.forgepack.library.internal.utils.Information;
import dev.forgepack.library.internal.utils.QRCode;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Service responsible for managing {@link User} entities.
 *
 * <p>Extends {@link ServiceGenericImpl} by inheriting the full CRUD operations
 * for the entity {@link User}, and implements {@link UniqueCheckable}
 * to provide uniqueness checks used during data validation.</p>
 *
 * <h3>Main responsibilities:</h3>
 * <ul>
 *     <li>Provide CRUD operations for {@link User}</li>
 *     <li>Validate uniqueness constraints for User attributes</li>
 *     <li>Support paginated and filtered queries</li>
 *     <li>Integrate entity–DTO mapping through {@link Mapper}</li>
 * </ul>
 *
 * <h3>Supported uniqueness fields.</h3>
 * <ul>
 *     <li>{@code name} - Checks for duplicates, ignoring case.</li>
 * </ul>
 *
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 *
 * @see ServiceGenericImpl
 * @see ServiceGeneric
 * @see UniqueCheckable
 * @see RepositoryUser
 * @see User
 * @see DTORequestUser
 * @see DTOResponseUser
 */
@Service
public class ServiceUser extends ServiceGenericImpl<User, DTORequestUser, DTOResponseUser> implements UniqueCheckable {

    private final E2EE e2EE;
    private final ServiceAuthenticationImpl serviceAuthenticationImpl;
    private final RepositoryUser repositoryUser;
    private final RepositoryRole repositoryRole;
    private final PasswordEncoder passwordEncoder;
    private final ServiceEmail serviceEmail;
    private final Mapper<User, DTORequestUser, DTOResponseUser> mapper;
    private static final Logger log = LoggerFactory.getLogger(Information.class);

    public ServiceUser(RepositoryGeneric<User> repositoryGeneric, E2EE e2EE, ServiceAuthenticationImpl serviceAuthenticationImpl, ServiceEmailImpl serviceEmail, Mapper<User, DTORequestUser, DTOResponseUser> mapperInterface, RepositoryUser repositoryUser, RepositoryRole repositoryRole, PasswordEncoder passwordEncoder) {
        super(User.class, repositoryGeneric, mapperInterface);
        this.e2EE = e2EE;
        this.serviceAuthenticationImpl = serviceAuthenticationImpl;
        this.repositoryUser = repositoryUser;
        this.repositoryRole = repositoryRole;
        this.serviceEmail = serviceEmail;
        this.mapper = mapperInterface;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DTOResponseUser create(DTORequestUser created){
        User user = mapper.toEntity(created);
        System.out.println("Username: " + user.getUsername());
        String password = generateSecurePassword();
        System.out.println("Password: " + password);
        String secret = serviceAuthenticationImpl.generateSecret();
        System.out.println("Secret: " + secret);
        try {
            user.setPassword(passwordEncoder.encode(password));
            user.setSecret(e2EE.encrypt(secret));
            Set<Role> roles = new HashSet<>();
            roles.add(repositoryRole.findByName("VIEWER").orElseThrow(() -> new RuntimeException("Default role VIEWER not found")));
            user.setRole(roles);
            user.setActive(true);
            user.setAttempt(0);
            byte[] qrCodeBytes = QRCode.generateQRCodeBytes(serviceAuthenticationImpl.buildSecretUri(user.getUsername(), user.getSecret()), 200);
            String emailContent = serviceEmail.buildWelcomeEmailContent(user.getUsername(), password, secret);
            serviceEmail.sendHtmlMessageWithAttachment(user.getEmail(), "Account Created", emailContent, qrCodeBytes, "qrcode.png", "image/png");
        } catch (MailException e) {
            log.error("Error sending email for {}: {}", user.getUsername(), e.getMessage());
            throw new BadCredentialsException("Failed to send welcome email");
        } catch (Exception e) {
            log.error("Error generating TOTP secret for {}: {}", created, e.getMessage(), e);
            throw new BadCredentialsException("Invalid secret");
        }
        log.info("{} creating a new user", new Information().getCurrentUser().orElse("Unknown User"));
        return mapper.toResponse(repositoryUser.save(user));
    }
    /**
     * Checks if a record exists with the specified value in a given field.
     *
     * @param field {@link String} name of the field to be checked.
     * @param value {@link Object} The value that will be compared in the specified field.
     * @return  {@code true} if another record exists with the same value in the specified field;
     *          {@code false} otherwise.
     * @throws IllegalArgumentException if the specified field is not supported.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * boolean exists = service.existsByField("name", "Admin");
     * }</pre>
     */
    @Override
    public boolean existsByField(String field, Object value) {
        if ("username".equals(field)) {
            return repositoryUser.existsByUsernameIgnoreCase((String) value);
        }
        if ("email".equals(field)) {
            return repositoryUser.existsByUsernameIgnoreCase((String) value);
        }
        else {
            throw new IllegalArgumentException("Invalid argument");
        }
    }
    /**
     * Checks if a record exists with the specified value in a given field, excluding the record with the given {@code id} from the check
     *
     * @param field {@link String} name of the field to be checked
     * @param value {@link Object} The value that will be compared in the specified field
     * @param id {@link UUID} Identifier of the record that should be ignored in the verification
     * @return  {@code true} if another record exists with the same value in the specified field;
     *          {@code false} otherwise
     * @throws IllegalArgumentException if the specified field is not supported
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * boolean exists = service.existsByFieldAndIdNot("name", "Admin", someUUID);
     * }</pre>
     */
    @Override
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("username".equals(field)){
            return repositoryUser.existsByUsernameIgnoreCaseAndIdNot((String) value, id);
        }
        if ("email".equals(field)){
            return repositoryUser.existsByUsernameIgnoreCaseAndIdNot((String) value, id);
        } else {
            throw new IllegalArgumentException("Field must not be null or empty.");
        }
    }
    public DTOResponseUser changePassword(DTORequestUserAuth updated){
        User user = isValidToChange(updated.id());
        Objects.requireNonNull(user).setPassword(passwordEncoder.encode(updated.password()));
        repositoryUser.save(user);
        log.info("{} changing user password with ID: {}", new Information().getCurrentUser().orElse("Unknown User"), user.getId());
        return mapper.toResponse(user);
    }
    public DTOResponseUser resetSecret(String username) {
        User user = isValidToChange(username);
        String secret = serviceAuthenticationImpl.generateSecret();
        try {
            user.setSecret(e2EE.encrypt(secret));
            repositoryUser.save(user);
            byte[] qrCodeBytes = QRCode.generateQRCodeBytes(serviceAuthenticationImpl.buildSecretUri(user.getUsername(), user.getSecret()), 200);
            String emailContent = serviceEmail.buildWelcomeEmailContent(user.getUsername(), "Your password is the same as before", secret);
            serviceEmail.sendHtmlMessageWithAttachment(user.getEmail(), "Reset TOTP requested", emailContent, qrCodeBytes, "qrcode.png", "image/png");
            log.info("{} resetting user secret with ID: {}", new Information().getCurrentUser().orElse("Unknown User"), user.getId());
            return mapper.toResponse(user);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to reset TOTP for user: " + user.getUsername());
        }
    }
    public DTOResponseUser resetPassword(String username) {
        User user = isValidToChange(username);
        String password = generateSecurePassword();
        user.setPassword(passwordEncoder.encode(password));
        repositoryUser.save(user);
        log.info("{} changing user password with ID: {}", new Information().getCurrentUser().orElse("Unknown User"), user.getId());
        return mapper.toResponse(user);
    }
    public String generateSecurePassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()-_=+[]{}|;:,.<>?";

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(special.charAt(random.nextInt(special.length())));
        String allChars = upper + lower + digits + special;
        for (int i = 4; i < 8; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        char[] chars = password.toString().toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
    public User isValidToChange(UUID id) {
        String currentUser = new Information().getCurrentUser().orElse("Unknown User");
        User user = repositoryUser.findById(id).orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        User userCurrent = repositoryUser.findByUsername(currentUser).orElseThrow(() -> new EntityNotFoundException("Current user not found"));
        if (userCurrent.getUsername() != null && user.getUsername() != null &&
                userCurrent.getUsername().equals(user.getUsername()) ||
                userCurrent.getRole().stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
            return user;
        } else {
            log.warn("{} attempted unauthorized access to user with ID: {}", currentUser, id);
            throw new EntityNotFoundException("Resource not found");
        }
    }
    public User isValidToChange(String username) {
        try {
            repositoryUser.findByUsername(username.trim()).orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        } catch (Exception e) {
            throw new EntityNotFoundException("Resource not found");
        }
        User user = repositoryUser.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        if (user.getUsername() != null) {
            return user;
        } else {
            log.warn("{} attempted unauthorized access to user with username: {}", new Information().getCurrentUser().orElse("Unknown User"), username);
            throw new EntityNotFoundException("Resource not found");
        }
    }
}
