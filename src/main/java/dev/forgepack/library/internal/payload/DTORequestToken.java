package dev.forgepack.library.internal.payload;

import dev.forgepack.library.api.payload.DTORequestIdentifiable;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Request DTO for Token entity
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public record DTORequestToken (

    UUID id,
    String tokenType,
    String accessToken,
    @NotNull
    UUID refreshToken
) implements DTORequestIdentifiable {

    public DTORequestToken(UUID id, String accessToken, UUID refreshToken) {
        this(id, "Bearer ", accessToken, refreshToken);
    }
}
