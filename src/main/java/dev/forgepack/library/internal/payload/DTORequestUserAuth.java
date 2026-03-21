package dev.forgepack.library.internal.payload;

import dev.forgepack.library.api.annotation.HasDigit;
import dev.forgepack.library.api.annotation.HasLetter;
import dev.forgepack.library.api.annotation.HasUpperCase;
import dev.forgepack.library.api.annotation.HasLowerCase;
import dev.forgepack.library.api.annotation.HasLength;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for UserAuth entity
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public record DTORequestUserAuth(
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    String username,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    @HasDigit
    @HasLetter
    @HasUpperCase
    @HasLowerCase
    @HasLength
    String password
//    @NotNull(message = "{not.null}")
//    Integer totpKey,
//    String captchaToken
) {}
