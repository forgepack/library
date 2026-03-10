package dev.forgepack.library.api;

import dev.forgepack.library.api.exception.annotation.Unique;
import dev.forgepack.library.api.service.ServiceRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Request DTO for Role entity
 *
 * @author	Marcelo Ribeiro Gadelha
 * Email:	gadelha.ti@gmail.com
 * Website:	www.forgepack.dev
 **/

@Unique(service = ServiceRole.class, field = "name")
public record DTORequestRole (

        UUID id,
        @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
        String name
) implements DTORequestIdentifiable {}
