package dev.forgepack.library.api.validator;

import dev.forgepack.library.api.annotation.HasUpperCase;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Contract for validators that enforce the {@link HasUpperCase} constraint.
 *
 * <p>Implementations are responsible for verifying whether a string contains
 * at least one uppercase letter ({@code A-Z}).</p>
 *
 * <p>Null values are considered valid and must not trigger a constraint violation.</p>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see HasUpperCase
 * @see ConstraintValidator
 */
public interface HasUpperCaseValidator extends ConstraintValidator<HasUpperCase, String> {

    /**
     * Validates whether the provided string contains at least one uppercase letter.
     *
     * @param value string to be validated
     * @param context validation context
     * @return {@code true} if the string contains at least one uppercase letter or is {@code null};
     *         {@code false} otherwise
     */
    boolean isValid(String value, ConstraintValidatorContext context);
}
