package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.validator.HasLengthValidator;
import jakarta.validation.ConstraintValidatorContext;

import static dev.forgepack.library.internal.validator.Validator.hasLength;

/**
 * Default implementation of {@link HasLengthValidator}.
 */
public class ValidatorHasLength implements HasLengthValidator {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return hasLength(8, value);
    }
}
