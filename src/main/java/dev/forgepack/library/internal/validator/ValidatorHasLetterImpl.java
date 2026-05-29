package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.validator.Validator;
import dev.forgepack.library.api.validator.ValidatorHasLetter;
import jakarta.validation.ConstraintValidatorContext;

public class ValidatorHasLetterImpl implements ValidatorHasLetter {

    protected Validator validator;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validator.hasLetter(value);
    }
}
