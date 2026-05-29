package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.validator.HasUpperCaseValidator;
import jakarta.validation.ConstraintValidatorContext;

import static dev.forgepack.library.internal.validator.Validator.hasUpperCase;

/**
 * Default implementation of {@link HasUpperCaseValidator}.
 */
public class ValidatorHasUpperCase implements HasUpperCaseValidator {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return hasUpperCase(value);
    }
}
