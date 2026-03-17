package dev.forgepack.library.api.annotation;

import dev.forgepack.library.internal.validator.Validator;
import dev.forgepack.library.internal.validator.ValidatorHasDigit;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

/**
 * Bean Validation constraint that verifies whether a string contains
 * at least one numeric digit.
 *
 * <p>This constraint validates that the annotated field includes at least
 * one numeric character ({@code 0-9}). The validation logic delegates the
 * verification to {@link Validator#hasDigit(String)}.</p>
 *
 * <p>This constraint can be applied to string fields that require the
 * presence of numeric characters, such as passwords, identifiers,
 * or formatted codes.</p>
 *
 * <h3>Validation rules</h3>
 * <ul>
 *     <li>The value must contain at least one numeric digit</li>
 *     <li>{@code null} values are considered valid</li>
 * </ul>
 *
 * <h3>Example</h3>
 * <pre>{@code
 * @HasDigit
 * private String password;
 * }</pre>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see Validator#hasDigit(String)
 * @see Constraint
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidatorHasDigit.class })
@Documented
public @interface HasDigit {

    String message() default "{has.digit}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
