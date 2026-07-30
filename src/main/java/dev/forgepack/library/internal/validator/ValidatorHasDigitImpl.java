package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.validator.Validator;
import dev.forgepack.library.api.validator.ValidatorHasDigit;
import jakarta.validation.ConstraintValidatorContext;

public class ValidatorHasDigitImpl implements ValidatorHasDigit {

    private final Validator validator = ValidatorImpl.INSTANCE;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return validator.hasDigit(value);
    }
}
