package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.annotation.HasDigit;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static dev.forgepack.library.internal.validator.Validator.hasDigit;

/**
 * This class implements the interface {@link ConstraintValidator}
 * <p>
 * Validator implementation that checks whether a string
 * contains at least one numeric digit.
 * </p>
 */
public class ValidatorHasDigit implements ConstraintValidator<HasDigit, String> {

    /**
     * Validates whether the provided string contains at least one digit.
     *
     * @param value string to be validated
     * @param context validation context
     * @return {@code true} if the string contains a numeric digit;
     *         {@code false} otherwise
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return hasDigit(value);
    }
}
