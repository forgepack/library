package dev.forgepack.library.api.exception.annotation;

import dev.forgepack.library.api.exception.UniqueCheckable;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.RetentionPolicy;

/**
 * Unique Annotation
 *
 * @author	Marcelo Ribeiro Gadelha
 * Email:	gadelha.ti@gmail.com
 * Website:	www.forgepack.dev
 **/

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueValidator.class })
@Documented
public @interface Unique {

    String message() default "{unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String field() default "name";
    String idField() default "id";
    Class<? extends UniqueCheckable> service();
}
