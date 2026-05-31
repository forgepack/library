package dev.forgepack.library.internal.configuration;

import dev.forgepack.library.internal.utils.Information;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Component responsible for JWT generation, parsing, and validation.
 *
 * <p>Tokens are signed with {@code HmacSHA512} using the secret configured via
 * {@code application.jwtSecret}. If no secret is configured, a random in-memory
 * key is generated and a warning is logged (all tokens are invalidated on restart).</p>
 *
 * <p>Configurable properties:</p>
 * <ul>
 *     <li>{@code application.jwtIssuer} – token issuer claim</li>
 *     <li>{@code application.jwtAudience} – token audience claim</li>
 *     <li>{@code application.jwtExpiration} – token lifetime in milliseconds</li>
 *     <li>{@code application.jwtSecret} – HMAC signing secret (min. 64 bytes recommended)</li>
 * </ul>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 */
@Component
public class ConfigurationJWT {

    @Value("${application.jwtIssuer}")
    private String issuer;
    @Value("${application.jwtAudience}")
    private String audience;
    @Value("${application.jwtExpiration}")
    private Integer expiration;
    @Value("${application.jwtSecret}")
    private String secretKey;
    private static final Logger log = LoggerFactory.getLogger(Information.class);

    /**
     * Builds the {@link SecretKey} used to sign and verify JWT tokens.
     *
     * <p>Falls back to a random in-memory key when {@code application.jwtSecret}
     * is not configured, logging a warning to alert about the insecure state.</p>
     *
     * @return the {@link SecretKey} for HMAC-SHA512 signing
     */
    private SecretKey getSigningKey() {
        if (secretKey == null || secretKey.isBlank()) {
            log.warn("JWT secret key not configured. Using random in-memory key");
            byte[] randomKey = new byte[64];
            new SecureRandom().nextBytes(randomKey);
            return new SecretKeySpec(randomKey, "HmacSHA512");
        }
        return new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
    }

    /**
     * Generates a signed JWT for the given subject (username).
     *
     * @param authentication the subject to embed in the token (typically the username)
     * @return the compact serialized JWT string
     */
    public String generateToken(String authentication) {
        return Jwts.builder()
                .audience().add(audience).and()
                .header().add("typ", "JWT").and()
                .issuer(issuer)
                .subject(authentication)
                .notBefore(new Date())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration))
                .signWith(getSigningKey())
                .compact();
    }
    /**
     * Extracts the subject (username) from a signed JWT.
     *
     * @param token the compact serialized JWT string
     * @return the username stored in the token's subject claim
     */
    public String getUsernameFromJWT(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    /**
     * Validates a JWT by verifying its signature and claims.
     *
     * <p>Returns {@code false} and logs the cause for any of the following conditions:
     * invalid signature, malformed token, expired token, unsupported token, or
     * empty claims string.</p>
     *
     * @param token the compact serialized JWT string
     * @return {@code true} if the token is valid; {@code false} otherwise
     */
    public boolean validateJWT(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey()).build()
                    .parseSignedClaims(token).getPayload();
            return true;
        } catch (SecurityException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e) {
            log.error("validateToken, exception: {}", e.getMessage());
        }
        return false;
    }
}
