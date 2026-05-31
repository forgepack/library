package dev.forgepack.library.internal.configuration;

import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.internal.service.ServiceAuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Enables JPA auditing and exposes the {@link AuditorAware} bean used to
 * resolve the currently authenticated user as the entity auditor.
 *
 * <p>Activates {@code @CreatedBy} and {@code @LastModifiedBy} population on
 * audited entities via {@link ServiceAuditorAwareImpl}.</p>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see ServiceAuditorAwareImpl
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ConfigurationAudit {

    /**
     * Provides the {@link AuditorAware} implementation that supplies the
     * current {@link User} for JPA auditing fields.
     *
     * @return a new {@link ServiceAuditorAwareImpl} instance
     */
    @Bean
    public AuditorAware<User> auditorAware() {
        return new ServiceAuditorAwareImpl();
    }
}
