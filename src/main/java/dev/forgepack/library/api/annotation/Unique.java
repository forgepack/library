package dev.forgepack.library.api.annotation;

import dev.forgepack.library.api.validator.UniqueCheckable;
import dev.forgepack.library.internal.validator.ValidatorUnique;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.RetentionPolicy;

/**
 * Bean Validation constraint used to verify the uniqueness of a field value.
 *
 * <p>This annotation can be applied to a class to ensure that the value of a
 * specified field is unique within the persistence layer. The validation
 * process delegates the uniqueness check to a service that implements
 * {@link UniqueCheckable}.</p>
 * 
 * <p>This constraint supports both creation and update scenarios. During update
 * operations, the current entity identifier can be excluded from the check
 * using the {@code idField} parameter.</p>
 *
 * <h3>Configuration parameters</h3>
 * <ul>
 *     <li>{@code field} – name of the field whose value must be unique</li>
 *     <li>{@code idField} – identifier field used to exclude the current entity during updates</li>
 *     <li>{@code service} – service responsible for performing the uniqueness check</li>
 *     <li>{@code message} – validation error message</li>
 * </ul>
 * 
 * <p><b>Example:</b></p>
 * <pre>{@code
 * @Unique(service = RoleService.class, field = "name")
 * public class DTORequestRole {
 *     private String name;
 * }
 * }</pre>
 *
 * <pre>{@code
 * @Unique(service = RoleService.class, field = "name", idField = "id")
 * public class DTORequestRole {
 *     private UUID id;
 *     private String name;
 * }
 * }</pre>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * 
 * @see ValidatorUnique
 * @see UniqueCheckable
 * @see Constraint
 */

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidatorUnique.class })
@Documented
public @interface Unique {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String field() default "name";
    String idField() default "id";
    Class<? extends UniqueCheckable> service();
}
