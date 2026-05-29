package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.validator.Validator;
import dev.forgepack.library.api.validator.ValidatorHasUpperCase;
import jakarta.validation.ConstraintValidatorContext;

public class ValidatorHasUpperCaseImpl implements ValidatorHasUpperCase {

    protected Validator validator;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validator.hasUpperCase(value);
    }
}
