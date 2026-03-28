package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.api.service.ServiceInterfacePassword;
import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.internal.payload.DTORequestUser;
import dev.forgepack.library.internal.payload.DTORequestUserAuth;
import dev.forgepack.library.internal.payload.DTOResponseUser;
import dev.forgepack.library.internal.repository.RepositoryUser;
import dev.forgepack.library.internal.utils.Information;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Objects;

@Service
public class ServicePassword implements ServiceInterfacePassword {

    private final PasswordEncoder passwordEncoder;
    private final RepositoryUser repositoryUser;
    private final ServiceUser serviceUser;
    private final Mapper<User, DTORequestUser, DTOResponseUser> mapper;
    private static final Logger log = LoggerFactory.getLogger(Information.class);

    public ServicePassword(PasswordEncoder passwordEncoder, RepositoryUser repositoryUser, ServiceUser serviceUser, Mapper<User, DTORequestUser, DTOResponseUser> mapper) {
        this.passwordEncoder = passwordEncoder;
        this.repositoryUser = repositoryUser;
        this.serviceUser = serviceUser;
        this.mapper = mapper;
    }

    @Override
    public DTOResponseUser changePassword(DTORequestUserAuth updated){
        User user = serviceUser.isValidToChange(updated.id());
        Objects.requireNonNull(user).setPassword(passwordEncoder.encode(updated.password()));
        repositoryUser.save(user);
        log.info("{} changing user password with ID: {}", new Information().getCurrentUser().orElse("Unknown User"), user.getId());
        return mapper.toResponse(user);
    }
    @Override
    public DTOResponseUser resetPassword(String username) {
        User user = serviceUser.isValidToChange(username);
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
}
