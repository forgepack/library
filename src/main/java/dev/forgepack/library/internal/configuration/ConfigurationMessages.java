package dev.forgepack.library.internal.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

/**
 * Configures the {@link MessageSource} bean used for resolving validation messages
 * and internationalized strings.
 *
 * <p>Loads message bundles from {@code ValidationMessages} resource files,
 * supporting locale-specific files (e.g., {@code ValidationMessages_pt_BR.properties})
 * with {@code UTF-8} encoding.</p>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 */
@Configuration
public class ConfigurationMessages {

    /**
     * Provides a {@link ResourceBundleMessageSource} that resolves messages from
     * {@code ValidationMessages} property files with UTF-8 encoding.
     *
     * @return the configured {@link MessageSource} bean
     */
    @Bean
    @Description("Spring Message Resolver")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }
}
