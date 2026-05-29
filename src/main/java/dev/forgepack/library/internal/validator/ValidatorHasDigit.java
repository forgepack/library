package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.validator.HasDigitValidator;
import jakarta.validation.ConstraintValidatorContext;

import static dev.forgepack.library.internal.validator.Validator.hasDigit;

/**
 * Default implementation of {@link HasDigitValidator}.
 */
public class ValidatorHasDigit implements HasDigitValidator {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return hasDigit(value);
    }
}
