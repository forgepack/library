package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.annotation.HasLength;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static dev.forgepack.library.internal.validator.Validator.hasLength;

/**
 * This class implements the interface {@link ConstraintValidator}
 * <p>
 * Validator implementation that checks whether a string
 * has the minimum required length.
 * </p>
 */
public class ValidatorHasLength implements ConstraintValidator<HasLength, String> {

    /**
     * Validates whether the provided string has the minimum required length.
     *
     * @param value string to be validated
     * @param context validation context
     * @return {@code true} if the string contains a numeric digit;
     *         {@code false} otherwise
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return hasLength(8, value);
    }
}
