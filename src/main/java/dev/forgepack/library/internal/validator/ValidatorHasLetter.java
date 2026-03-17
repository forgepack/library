package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.annotation.HasLetter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static dev.forgepack.library.internal.validator.Validator.hasLetter;

/**
 * This class implements the interface {@link ConstraintValidator}
 * <p>
 * Validator implementation that checks whether a string
 * contains at least one letter.
 * </p>
 */
public class ValidatorHasLetter implements ConstraintValidator<HasLetter, String> {

    /**
     * Validates whether the provided string contains at least one letter.
     *
     * @param value string to be validated
     * @param context validation context
     * @return {@code true} if the string contains a numeric digit;
     *         {@code false} otherwise
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return hasLetter(value);
    }
}
