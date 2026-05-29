package dev.forgepack.library.api.validator;

import dev.forgepack.library.api.annotation.HasLength;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Contract for validators that enforce the {@link HasLength} constraint.
 *
 * <p>Implementations are responsible for verifying whether a string meets
 * the minimum required length.</p>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see HasLength
 * @see ConstraintValidator
 */
public interface HasLengthValidator extends ConstraintValidator<HasLength, String> {

    /**
     * Validates whether the provided string meets the minimum required length.
     *
     * @param value string to be validated
     * @param context validation context
     * @return {@code true} if the string meets the minimum required length;
     *         {@code false} otherwise
     */
    boolean isValid(String value, ConstraintValidatorContext context);
}
