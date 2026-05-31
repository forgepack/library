package dev.forgepack.library.internal.service;

import dev.forgepack.library.internal.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Spring Data auditing implementation that resolves the currently authenticated user.
 *
 * <p>This service integrates with the Spring Security context to provide the
 * {@link User} principal as the current auditor, enabling automatic population
 * of auditing fields (e.g., {@code createdBy}, {@code lastModifiedBy}) on
 * audited entities.</p>
 *
 * <p>If no authenticated user is present, or if the principal is not an instance
 * of {@link User}, an empty {@link Optional} is returned and no auditor is set.</p>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see AuditorAware
 * @see User
 */
@Service
public class ServiceAuditorAwareImpl implements AuditorAware<User> {

    /**
     * Returns the currently authenticated {@link User} as the active auditor.
     *
     * <p>The auditor is resolved from the {@link SecurityContextHolder}. An empty
     * {@link Optional} is returned when no authentication is present or when the
     * principal is not a {@link User} instance.</p>
     *
     * @return an {@link Optional} containing the authenticated {@link User},
     *         or {@link Optional#empty()} if no valid auditor is available
     */
    @Override @NonNull
    public Optional<User> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(auth -> auth.isAuthenticated() && auth.getPrincipal() instanceof User)
                .map(auth -> (User) auth.getPrincipal());
    }
}
