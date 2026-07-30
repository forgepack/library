package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.validator.Validator;
import dev.forgepack.library.api.validator.ValidatorHasUpperCase;
import jakarta.validation.ConstraintValidatorContext;

public class ValidatorHasUpperCaseImpl implements ValidatorHasUpperCase {

    private final Validator validator = ValidatorImpl.INSTANCE;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validator.hasUpperCase(value);
    }
}
