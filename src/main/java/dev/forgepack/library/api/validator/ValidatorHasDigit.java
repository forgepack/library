package dev.forgepack.library.api.validator;

import dev.forgepack.library.api.annotation.HasDigit;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Contract for validators that enforce the {@link HasDigit} constraint.
 *
 * <p>Implementations are responsible for verifying whether a string contains
 * at least one numeric digit ({@code 0-9}).</p>
 *
 * <p>Null values are considered valid and must not trigger a constraint violation.</p>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see HasDigit
 * @see ConstraintValidator
 */
public interface ValidatorHasDigit extends ConstraintValidator<HasDigit, String> {

    /**
     * Validates whether the provided string contains at least one digit.
     *
     * @param value string to be validated
     * @param context validation context
     * @return {@code true} if the string contains a numeric digit or is {@code null};
     *         {@code false} otherwise
     */
    boolean isValid(String value, ConstraintValidatorContext context);
}
