package dev.forgepack.library.api.annotation;

import dev.forgepack.library.internal.validator.ValidatorImpl;
import dev.forgepack.library.internal.validator.ValidatorHasLowerCaseImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

/**
 * Bean Validation constraint that verifies whether a string contains
 * at least one lowercase letter.
 *
 * <p>This constraint validates that the annotated field includes at least
 * one lowercase letter ({@code a-z}). The validation logic delegates the
 * verification to {@link ValidatorImpl#hasLowerCase(String)}.</p>
 *
 * <p>This constraint can be applied to string fields that require the
 * presence of lowercase letters, such as passwords, identifiers,
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
 * @HasLowerCase
 * private String password;
 * }</pre>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see ValidatorImpl#hasLowerCase(String)
 * @see Constraint
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidatorHasLowerCaseImpl.class })
@Documented
public @interface HasLowerCase {

    String message() default "{has.lower.case}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
