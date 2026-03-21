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

    private SecretKey getSigningKey() {
        if (secretKey == null || secretKey.isBlank()) {
            log.warn("JWT secret key not configured. Using random in-memory key");
            byte[] randomKey = new byte[64];
            new SecureRandom().nextBytes(randomKey);
            return new SecretKeySpec(randomKey, "HmacSHA512");
        }
        return new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
    }

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
    public String getUsernameFromJWT(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
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
