package dev.forgepack.library.internal.configuration;

import dev.forgepack.library.internal.configuration.filter.FilterJwt;
import dev.forgepack.library.internal.service.ServiceCustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * Spring Security configuration for the application.
 *
 * <p>Configures a stateless, JWT-based security model with the following characteristics:</p>
 * <ul>
 *     <li>CSRF disabled (REST API with stateless sessions)</li>
 *     <li>Strict security headers: {@code X-Frame-Options: DENY}, CSP, HSTS, and Referrer-Policy</li>
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

    private final ConfigurationJwt configurationJwt;
    private final ServiceCustomUserDetails serviceCustomUserDetails;
    @Value("${application.endpoints}")
    private String[] endpoints;

    public ConfigurationSecurity(ConfigurationJwt configurationJwt, ServiceCustomUserDetails serviceCustomUserDetails) {
        this.configurationJwt = configurationJwt;
        this.serviceCustomUserDetails = serviceCustomUserDetails;
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
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for REST APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'none'; frame-ancestors 'none'; connect-src 'self'")
                        )
                        .referrerPolicy(referrer -> referrer
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        )
                        .httpStrictTransportSecurity(hsts -> hsts
                                .maxAgeInSeconds(31536000)
                                .includeSubDomains(true)
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/auth/resetPassword").permitAll()
                        .requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers(endpoints).authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
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
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * Creates and returns the {@link FilterJwt} bean responsible for
     * extracting and validating JWTs on each request.
     *
     * @return a new {@link FilterJwt} instance
     */
    @Bean
    public FilterJwt jwtAuthenticationFilter() {
        return new FilterJwt(configurationJwt, serviceCustomUserDetails);
    }
}
