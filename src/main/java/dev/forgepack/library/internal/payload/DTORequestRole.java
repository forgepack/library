package dev.forgepack.library.internal.payload;

import dev.forgepack.library.api.annotation.Unique;
import dev.forgepack.library.api.payload.DTORequestIdentifiable;
import dev.forgepack.library.internal.service.ServiceRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

/**
 * Request DTO for Role entity
 *
 * @author Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

@Unique(service = ServiceRole.class, field = "name")
public record DTORequestRole(

        UUID id,
        @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
        String name,
        Set<DTOResponsePrivilege> privilege
) implements DTORequestIdentifiable<UUID> {
}
