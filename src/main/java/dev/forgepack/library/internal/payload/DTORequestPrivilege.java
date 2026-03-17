package dev.forgepack.library.internal.payload;

import java.util.UUID;

import dev.forgepack.library.api.annotation.Unique;
import dev.forgepack.library.api.payload.DTORequestIdentifiable;
import dev.forgepack.library.internal.service.ServicePrivilege;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for Privilege entity
 *
 * @author Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

@Unique(service = ServicePrivilege.class, field = "name")
public record DTORequestPrivilege(

        UUID id,
        @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
        String name
) implements DTORequestIdentifiable {
}
