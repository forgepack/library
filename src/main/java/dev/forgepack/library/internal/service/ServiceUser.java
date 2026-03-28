package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.api.repository.RepositoryInterface;
import dev.forgepack.library.api.service.ServiceInterfaceEmail;
import dev.forgepack.library.api.validator.UniqueCheckable;
import dev.forgepack.library.api.service.ServiceInterface;
import dev.forgepack.library.internal.model.Role;
import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.internal.payload.DTORequestUser;
import dev.forgepack.library.internal.payload.DTOResponseUser;
import dev.forgepack.library.internal.repository.RepositoryLog;
import dev.forgepack.library.internal.repository.RepositoryRole;
import dev.forgepack.library.internal.repository.RepositoryUser;
import dev.forgepack.library.internal.utils.Information;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Service responsible for managing {@link User} entities.
 *
 * <p>Extends {@link ServiceGeneric} by inheriting the full CRUD operations
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
 * @see ServiceGeneric
 * @see ServiceInterface
 * @see UniqueCheckable
 * @see RepositoryUser
 * @see User
 * @see DTORequestUser
 * @see DTOResponseUser
 */
@Service
public class ServiceUser extends ServiceGeneric<User, DTORequestUser, DTOResponseUser> implements UniqueCheckable {

    private final ServicePassword servicePassword;
    private final RepositoryUser repositoryUser;
    private final RepositoryRole repositoryRole;
    private final ServiceInterfaceEmail serviceEmail;
    private final Mapper<User, DTORequestUser, DTOResponseUser> mapper;
    private static final Logger log = LoggerFactory.getLogger(Information.class);

    public ServiceUser(RepositoryInterface<User> repositoryInterface, ServiceEmailImpl serviceEmail, Mapper<User, DTORequestUser, DTOResponseUser> mapperInterface, RepositoryUser repositoryUser, RepositoryLog repositoryLog, RepositoryRole repositoryRole, ServicePassword servicePassword) {
        super(User.class, repositoryInterface, mapperInterface, repositoryUser, repositoryLog);
        this.servicePassword = servicePassword;
        this.repositoryUser = repositoryUser;
        this.repositoryRole = repositoryRole;
        this.serviceEmail = serviceEmail;
        this.mapper = mapperInterface;
    }

    @Override
    public DTOResponseUser create(DTORequestUser created){
        User user = mapper.toEntity(created);
        user = servicePassword.createPassword(user);
        System.out.println("Username: " + user.getUsername());
        try {
//            user.setSecret(e2EE.encrypt(secret));
            Set<Role> roles = new HashSet<>();
            roles.add(repositoryRole.findByName("VIEWER"));
            user.setRole(roles);
            user.setActive(true);
            user.setAttempt(0);
//            byte[] qrCodeBytes = QRCode.generateQRCodeBytes(buildTotpUri(user.getUsername(), user.getSecret()), 200);
            String emailContent = serviceEmail.buildWelcomeEmailContent(user.getUsername(), "password", "secret");
            serviceEmail.sendHtmlMessageWithAttachment(user.getEmail(), "Account Created", emailContent, /*qrCodeBytes, */"qrcode.png", "image/png");
//        } catch (MailException e) {
//            log.error("Error sending email for {}: {}", user.getUsername(), e.getMessage());
//            throw new BadCredentialsException("Failed to send welcome email");
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
     * @author Marcelo Ribeiro Gadelha
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
     * @author Marcelo Ribeiro Gadelha
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
}
