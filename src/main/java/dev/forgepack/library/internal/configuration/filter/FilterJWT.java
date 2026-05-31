package dev.forgepack.library.internal.configuration.filter;

import dev.forgepack.library.internal.configuration.ConfigurationJWT;
import dev.forgepack.library.internal.service.ServiceCustomUserDetails;
import dev.forgepack.library.internal.utils.Information;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

/**
 * JWT authentication filter that intercepts every HTTP request to validate
 * and establish the security context from a Bearer token.
 *
 * <p>Extends {@link OncePerRequestFilter} to guarantee single execution per request.
 * If a valid JWT is present in the {@code Authorization} header, the associated
 * user is loaded and set in the {@link SecurityContextHolder}.</p>
 *
 * <p>Regardless of the authentication outcome, the filter appends an
 * {@code X-API-Version} response header and always continues the filter chain.</p>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see ConfigurationJWT
 * @see ServiceCustomUserDetails
 * @see OncePerRequestFilter
 */
public class FilterJWT extends OncePerRequestFilter {

    @Value("${application.version}")
    private String version;
    public final ConfigurationJWT configurationJwt;
    public final ServiceCustomUserDetails serviceCustomUserDetails;
    private static final Logger log = LoggerFactory.getLogger(Information.class);

    public FilterJWT(ConfigurationJWT configurationJwt, ServiceCustomUserDetails serviceCustomUserDetails) {
        this.configurationJwt = configurationJwt;
        this.serviceCustomUserDetails = serviceCustomUserDetails;
    }

    /**
     * Processes each HTTP request to extract and validate a JWT from the
     * {@code Authorization: Bearer <token>} header.
     *
     * <p>When a valid token is found, the corresponding user is loaded via
     * {@link ServiceCustomUserDetails} and stored in the
     * {@link SecurityContextHolder}. Authentication errors are logged and
     * silently swallowed so the filter chain always continues.</p>
     *
     * <p>An {@code X-API-Version} header is appended to every response.</p>
     *
     * @param request     the incoming HTTP request
     * @param response    the HTTP response
     * @param filterChain the remaining filter chain
     * @throws ServletException if a servlet error occurs while processing the request
     * @throws IOException      if an I/O error occurs during filter execution
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            getJWTFromRequest(request)
                    .filter(configurationJwt::validateJWT)
                    .map(configurationJwt::getUsernameFromJWT)
                    .ifPresent(username->authenticateUser(username, request));
        } catch (Exception ex) {
            log.info("Unable to authenticate user: {}", ex.getMessage());
        }
        response.addHeader("X-API-Version", version);
        filterChain.doFilter(request, response);
    }
    private Optional<String> getJWTFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
    private void authenticateUser(String username, HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = serviceCustomUserDetails.loadUserByUsername(username);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }
}
