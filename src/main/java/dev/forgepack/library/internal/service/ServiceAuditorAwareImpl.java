package dev.forgepack.library.internal.service;

import dev.forgepack.library.internal.model.User;
import jakarta.persistence.EntityManager;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ServiceAuditorAwareImpl implements AuditorAware<User> {

    private final EntityManager entityManager;

    public ServiceAuditorAwareImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override @NonNull
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return Optional.empty();
        }
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", userDetails.getUsername())
                .getResultStream()
                .findFirst();
    }
}
