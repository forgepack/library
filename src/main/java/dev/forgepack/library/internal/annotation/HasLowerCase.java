package dev.forgepack.library.internal.annotation;

import dev.forgepack.library.internal.validator.Validator;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;
import static dev.forgepack.library.internal.validator.Validator.hasLowerCase;

/**
 * Bean Validation constraint that verifies whether a string contains
 * at least one lowercase letter.
 *
 * <p>This constraint validates that the annotated field includes at least
 * one lowercase letter ({@code a-zA-Z}). The validation logic delegates the
 * verification to {@link Validator#hasLetter(String)}.</p>
 *
 * <p>This constraint can be applied to string fields that require the
 * presence of numeric characters, such as passwords, identifiers,
 * or formatted codes.</p>
 *
 * <h3>Validation rules</h3>
 * <ul>
 *     <li>The value must contain at least one lowercase letter</li>
 *     <li>{@code null} values are considered valid</li>
 * </ul>
 *
 * <h3>Example</h3>
 * <pre>{@code
 * @HasLetter
 * private String password;
 * }</pre>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see Validator#hasLetter(String)
 * @see Constraint
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HasLowerCase.ValidatorHasLowerCase.class })
@Documented
public @interface HasLowerCase {

    String message() default "{has.lower.case}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    /**
     * This inner class implements the interface {@link ConstraintValidator}
     * <p>
     * Validator implementation that checks whether a string
     * contains at least one lowercase letter.
     * </p>
     */
    class ValidatorHasLowerCase implements ConstraintValidator<HasLowerCase, String> {

        /**
         * Validates whether the provided string contains at least one lowercase letter.
         *
         * @param value string to be validated
         * @param context validation context
         * @return {@code true} if the string contains a numeric digit;
         *         {@code false} otherwise
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return hasLowerCase(value);
        }
    }
}
