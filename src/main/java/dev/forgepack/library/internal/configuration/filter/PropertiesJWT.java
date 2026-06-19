package dev.forgepack.library.internal.configuration.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Configuration properties for JWT generation and validation.
 *
 * <p>Configurable via {@code application.properties} under the {@code forgepack.jwt}
 * prefix. All fields have sensible defaults and may be omitted by the consumer.</p>
 *
 * <p><strong>Warning:</strong> when {@code secret} is left blank, a random in-memory
 * key is generated at startup. All tokens are invalidated on every restart.</p>
 *
 * @param issuer     Token issuer claim. Default: {@code forgepack}.
 * @param audience   Token audience claim. Default: {@code forgepack-client}.
 * @param expiration Token lifetime in milliseconds. Default: {@code 3600000} (1 hour).
 * @param secret     HMAC-SHA512 signing secret. Minimum 64 bytes recommended. Default: blank (random key).
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 */
@ConfigurationProperties(prefix = "forgepack.jwt")
public record PropertiesJWT(
        @DefaultValue("forgepack")        String issuer,
        @DefaultValue("forgepack-client") String audience,
        @DefaultValue("3600000")          long   expiration,
        @DefaultValue("")                 String secret
) {}
