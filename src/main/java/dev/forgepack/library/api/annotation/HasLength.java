package dev.forgepack.library.api.annotation;

import dev.forgepack.library.internal.validator.Validator;
import dev.forgepack.library.internal.validator.ValidatorHasLength;
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
 * <p>This constraint validates that the annotated field includes the minimum required length.
 * The validation logic delegates the
 * verification to {@link Validator#hasLength(int, String)}.</p>
 *
 * <p>This constraint can be applied to string fields that require the
 * presence of numeric characters, such as passwords, identifiers,
 * or formatted codes.</p>
 *
 * <h3>Validation rules</h3>
 * <ul>
 *     <li>The value must has the minimum required length</li>
 *     <li>{@code null} values are considered valid</li>
 * </ul>
 *
 * <h3>Example</h3>
 * <pre>{@code
 * @HasLength
 * private String password;
 * }</pre>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see Validator#hasLength(int, String)
 * @see Constraint
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidatorHasLength.class })
@Documented
public @interface HasLength {

    String message() default "{has.length}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
