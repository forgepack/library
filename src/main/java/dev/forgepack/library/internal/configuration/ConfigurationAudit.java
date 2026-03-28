package dev.forgepack.library.internal.configuration;

import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.internal.service.ServiceAuditorAwareImpl;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ConfigurationAudit {
    private final EntityManager entityManager;

    public ConfigurationAudit(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Bean
    public AuditorAware<User> auditorAware() {
        return new ServiceAuditorAwareImpl(entityManager);
    }
}
