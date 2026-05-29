package dev.forgepack.library.api.validator;

import dev.forgepack.library.api.annotation.HasLetter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Contract for validators that enforce the {@link HasLetter} constraint.
 *
 * <p>Implementations are responsible for verifying whether a string contains
 * at least one letter ({@code a-zA-Z}).</p>
 *
 * <p>Null values are considered valid and must not trigger a constraint violation.</p>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see HasLetter
 * @see ConstraintValidator
 */
public interface ValidatorHasLetter extends ConstraintValidator<HasLetter, String> {

    /**
     * Validates whether the provided string contains at least one letter.
     *
     * @param value string to be validated
     * @param context validation context
     * @return {@code true} if the string contains at least one letter or is {@code null};
     *         {@code false} otherwise
     */
    boolean isValid(String value, ConstraintValidatorContext context);
}
