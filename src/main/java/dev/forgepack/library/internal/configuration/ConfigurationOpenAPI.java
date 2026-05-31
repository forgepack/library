package dev.forgepack.library.internal.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

/**
 * Configures the OpenAPI (Swagger) documentation bean for the application.
 *
 * <p>Reads API metadata from application properties ({@code application.name},
 * {@code application.version}, {@code openapi.url}) and produces an
 * {@link OpenAPI} bean that drives the Swagger UI and the
 * {@code /v3/api-docs} endpoint.</p>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 */
@Configuration
public class ConfigurationOpenAPI {
    @Value("${openapi.url}")
    private String url;
    @Value("${application.name}")
    private String name;
    @Value("${application.version}")
    private String version;

    /**
     * Builds and returns the {@link OpenAPI} definition with project metadata,
     * contact information, license, and server URL.
     *
     * @return the configured {@link OpenAPI} bean
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(name)
                        .version(version)
                        .contact(new Contact()
                                .name("Gadelha TI")
                                .url("https://github.com/gadelhati/"))
                        .description("This API exposes endpoints to manage researches.").termsOfService("https://www.forgepack.dev")
                        .license(new License()
                                .name("MIT License")
                                .url("https://choosealicense.com/licenses/mit/")))
                .servers(List.of(
                        new Server().url(url).description("Server URL in environment")));
    }
}
