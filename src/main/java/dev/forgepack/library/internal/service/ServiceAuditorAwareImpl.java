package dev.forgepack.library.internal.service;

import dev.forgepack.library.internal.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ServiceAuditorAwareImpl implements AuditorAware<User> {

    @Override @NonNull
    public Optional<User> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(auth -> auth.isAuthenticated() && auth.getPrincipal() instanceof User)
                .map(auth -> (User) auth.getPrincipal());
    }
}
