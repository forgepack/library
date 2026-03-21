package dev.forgepack.library.internal.payload;

import java.util.UUID;
import dev.forgepack.library.api.payload.DTORequestIdentifiable;
import dev.forgepack.library.api.annotation.HasDigit;
import dev.forgepack.library.api.annotation.HasLetter;
import dev.forgepack.library.api.annotation.HasUpperCase;
import dev.forgepack.library.api.annotation.HasLowerCase;
import dev.forgepack.library.api.annotation.HasLength;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for UserPassword entity
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public record DTORequestUserPassword (

    UUID id,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    @HasDigit
    @HasLetter
    @HasUpperCase
    @HasLowerCase
    @HasLength
    String password
) implements DTORequestIdentifiable<UUID> {}
