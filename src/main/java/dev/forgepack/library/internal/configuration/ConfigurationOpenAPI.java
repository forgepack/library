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

@Configuration
public class ConfigurationOpenAPI {
    @Value("${openapi.url}")
    private String url;
    @Value("${application.name}")
    private String name;
    @Value("${application.version}")
    private String version;

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
