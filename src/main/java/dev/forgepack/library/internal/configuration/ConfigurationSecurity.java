package dev.forgepack.library.internal.configuration;

import dev.forgepack.library.internal.configuration.filter.FilterJwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security configuration for the application.
 *
 * <p>Configures a stateless, JWT-based security model with the following characteristics:</p>
 * <ul>
 *     <li>CSRF disabled (REST API with stateless sessions)</li>
 *     <li>Security headers delegated entirely to {@link dev.forgepack.library.internal.configuration.filter.FilterSecurityHeaders}</li>
 *     <li>Public endpoints: {@code /auth/login}, {@code POST /user/**}, {@code /auth/resetPassword},
 *         actuator health/info, and Swagger UI paths</li>
 *     <li>All other requests require authentication</li>
 *     <li>{@link FilterJwt} inserted before {@link UsernamePasswordAuthenticationFilter}</li>
 *     <li>Role prefix removed via {@link GrantedAuthorityDefaults}</li>
 * </ul>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see FilterJwt
 * @see ConfigurationJwt
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfigurationSecurity {

    private final FilterJwt filterJwt;

    public ConfigurationSecurity(FilterJwt filterJwt) {
        this.filterJwt = filterJwt;
    }
    /**
     * Defines the main {@link SecurityFilterChain} with authorization rules,
     * security headers, and the JWT filter.
     *
     * @param httpSecurity the {@link HttpSecurity} builder
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/auth/resetPassword").permitAll()
                        .requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filterJwt, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    /**
     * Removes the default {@code ROLE_} prefix from granted authorities,
     * allowing role names to be used without the prefix in security expressions.
     *
     * @return a {@link GrantedAuthorityDefaults} with an empty prefix
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
    /**
     * Exposes the {@link AuthenticationManager} bean from the application context.
     *
     * @param authenticationConfiguration the Spring Security authentication configuration
     * @return the configured {@link AuthenticationManager}
     * @throws Exception if the manager cannot be resolved
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    /**
     * Provides the {@link PasswordEncoder} bean using BCrypt hashing.
     *
     * @return a {@link BCryptPasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
