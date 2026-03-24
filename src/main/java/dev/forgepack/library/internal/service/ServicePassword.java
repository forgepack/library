package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.api.service.ServiceInterfacePassword;
import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.internal.payload.DTORequestUser;
import dev.forgepack.library.internal.payload.DTORequestUserAuth;
import dev.forgepack.library.internal.payload.DTOResponseUser;
import dev.forgepack.library.internal.repository.RepositoryUser;
import dev.forgepack.library.internal.utils.Information;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.UUID;

@Service
public class ServicePassword implements ServiceInterfacePassword {

    private final PasswordEncoder passwordEncoder;
    private final RepositoryUser repositoryUser;
    private final Mapper<User, DTORequestUser, DTOResponseUser> mapper;
    private static final Logger log = LoggerFactory.getLogger(Information.class);

    public ServicePassword(PasswordEncoder passwordEncoder, RepositoryUser repositoryUser, Mapper<User, DTORequestUser, DTOResponseUser> mapper) {
        this.passwordEncoder = passwordEncoder;
        this.repositoryUser = repositoryUser;
        this.mapper = mapper;
    }

    @Override
    public DTOResponseUser changePassword(DTORequestUserAuth updated){
        User user = isValidToChange(updated.id());
        Objects.requireNonNull(user).setPassword(passwordEncoder.encode(updated.password()));
        repositoryUser.save(user);
        log.info("{} changing user password with ID: {}", new Information().getCurrentUser().orElse("Unknown User"), user.getId());
        return mapper.toResponse(user);
    }
    @Override
    public DTOResponseUser resetPassword(String username) {
        User user = isValidToChange(username);
        String password = generateSecurePassword();
        user.setPassword(passwordEncoder.encode(password));
        repositoryUser.save(user);
        log.info("{} changing user password with ID: {}", new Information().getCurrentUser().orElse("Unknown User"), user.getId());
        return mapper.toResponse(user);
    }
    public User createPassword(User created){
        String password = generateSecurePassword();
        created.setPassword(passwordEncoder.encode(password));
        return created;
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
            throw new EntityNotFoundException("i Resource not found");
        }
    }
}
