package dev.forgepack.library.internal.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

/**
 * Configuration properties for CORS (Cross-Origin Resource Sharing).
 *
 * <p>Configurable via {@code application.properties} under the {@code forgepack.cors}
 * prefix. All fields have sensible defaults and may be omitted by the consumer.</p>
 *
 * @param allowedOrigins  Origin patterns allowed to access the API. Default: {@code http://localhost:5173}.
 * @param allowedMethods  HTTP methods allowed in cross-origin requests.
 *                        Default: {@code GET, POST, PUT, PATCH, DELETE, OPTIONS}.
 * @param allowedHeaders  Headers allowed in cross-origin requests. Default: {@code *}.
 * @param allowCredentials Whether credentials (cookies, authorization headers) are allowed. Default: {@code true}.
 * @param maxAge          Pre-flight response cache duration in seconds. Default: {@code 3600}.
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 */
@ConfigurationProperties(prefix = "forgepack.cors")
public record CorsProperties(
        @DefaultValue("http://localhost:5173")                          List<String> allowedOrigins,
        @DefaultValue({"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"}) List<String> allowedMethods,
        @DefaultValue("*")                                              List<String> allowedHeaders,
        @DefaultValue("true")                                           boolean      allowCredentials,
        @DefaultValue("3600")                                           long         maxAge
) {}