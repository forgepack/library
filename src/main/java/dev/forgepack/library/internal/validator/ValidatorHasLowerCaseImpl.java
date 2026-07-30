package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.validator.Validator;
import dev.forgepack.library.api.validator.ValidatorHasLowerCase;
import jakarta.validation.ConstraintValidatorContext;

public class ValidatorHasLowerCaseImpl implements ValidatorHasLowerCase {

    private final Validator validator = ValidatorImpl.INSTANCE;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validator.hasLowerCase(value);
    }
}
