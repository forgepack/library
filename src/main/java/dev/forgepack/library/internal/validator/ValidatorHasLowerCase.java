package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.validator.HasLowerCaseValidator;
import jakarta.validation.ConstraintValidatorContext;

import static dev.forgepack.library.internal.validator.Validator.hasLowerCase;

/**
 * Default implementation of {@link HasLowerCaseValidator}.
 */
public class ValidatorHasLowerCase implements HasLowerCaseValidator {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return hasLowerCase(value);
    }
}
