package dev.forgepack.library.internal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Configures CORS (Cross-Origin Resource Sharing) for the application.
 *
 * <p>Reads allowed origins from the {@code application.cors} property (comma-separated).
 * Falls back to {@code *} when the property is not set, allowing all origins.
 * All standard HTTP methods and headers are permitted, credentials are allowed,
 * and the pre-flight cache lifetime is set to 3600 seconds.</p>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 */
@Configuration
public class ConfigurationCors {

    @Autowired
    private Environment environment;

    /**
     * Builds a {@link CorsConfigurationSource} applied to all paths ({@code /**}).
     *
     * <p>Allowed origin patterns are resolved from the {@code application.cors}
     * environment property. Multiple origins must be separated by commas.</p>
     *
     * @return the configured {@link CorsConfigurationSource} bean
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        String allowedOriginsStr = environment.getProperty("application.cors",
                "*");

        List<String> allowedOriginsList = Arrays.asList(allowedOriginsStr.split(","));
        configuration.setAllowedOriginPatterns(allowedOriginsList);

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}