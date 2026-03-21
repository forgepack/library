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
