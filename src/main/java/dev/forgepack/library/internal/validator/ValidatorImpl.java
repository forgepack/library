package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.validator.Validator;

public final class ValidatorImpl implements Validator {

    private ValidatorImpl() {
        throw new UnsupportedOperationException("Utility class");
    }

    public boolean isNull(Object value) {
        return value == null;
    }

    public boolean hasDigit(String value) {
        return !isNull(value) && value.chars().anyMatch(Character::isDigit);
    }

    public boolean hasLetter(String value) {
        return !isNull(value) && value.chars().anyMatch(Character::isLetter);
    }

    public boolean hasLowerCase(String value) {
        return !isNull(value) && value.chars().anyMatch(Character::isLowerCase);
    }

    public boolean hasUpperCase(String value) {
        return !isNull(value) && value.chars().anyMatch(Character::isUpperCase);
    }

    public boolean hasLength(int length, String value) {
        return !isNull(value) && value.length() >= length;
    }
}
