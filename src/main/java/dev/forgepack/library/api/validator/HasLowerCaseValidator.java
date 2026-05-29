package dev.forgepack.library.api.validator;

import dev.forgepack.library.api.annotation.HasLowerCase;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Contract for validators that enforce the {@link HasLowerCase} constraint.
 *
 * <p>Implementations are responsible for verifying whether a string contains
 * at least one lowercase letter ({@code a-z}).</p>
 *
 * <p>Null values are considered valid and must not trigger a constraint violation.</p>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see HasLowerCase
 * @see ConstraintValidator
 */
public interface HasLowerCaseValidator extends ConstraintValidator<HasLowerCase, String> {

    /**
     * Validates whether the provided string contains at least one lowercase letter.
     *
     * @param value string to be validated
     * @param context validation context
     * @return {@code true} if the string contains at least one lowercase letter or is {@code null};
     *         {@code false} otherwise
     */
    boolean isValid(String value, ConstraintValidatorContext context);
}
