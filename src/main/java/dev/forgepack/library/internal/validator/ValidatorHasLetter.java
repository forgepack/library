package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.validator.HasLetterValidator;
import jakarta.validation.ConstraintValidatorContext;

import static dev.forgepack.library.internal.validator.Validator.hasLetter;

/**
 * Default implementation of {@link HasLetterValidator}.
 */
public class ValidatorHasLetter implements HasLetterValidator {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return hasLetter(value);
    }
}
