package dev.forgepack.library.api;

import dev.forgepack.library.api.exception.annotation.Unique;
import dev.forgepack.library.api.service.ServiceRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.forgepack.dev
 **/

@Unique(service = ServiceRole.class, field = "name")
public record DTORequestRole (

        UUID id,
        @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
        String name
) implements DTORequestIdentifiable {}
