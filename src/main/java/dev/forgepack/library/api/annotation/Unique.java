package dev.forgepack.library.api.annotation;

import dev.forgepack.library.api.validator.UniqueCheckable;
import dev.forgepack.library.internal.validator.UniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotação para validação de unicidade de campos em entidades.
 * <p>
 * Esta anotação pode ser aplicada a classes para validar se um campo específico
 * possui valor único na base de dados. Suporta tanto validação para criação
 * quanto para atualização de entidades.
 * 
 * Parâmetros configuráveis:
 * <ul>
 *     <li>{@code field}: Nome do campo a ser verificado (padrão: "name")</li>
 *     <li>{@code idField}: Nome do campo ID para exclusão na atualização (padrão: "id")</li>
 *     <li>{@code service}: Classe do serviço que implementa UniqueCheckable</li>
 *     <li>{@code message}: Mensagem de erro customizada</li>
 * </ul>
 * 
 * Exemplo de uso:
 * <pre>
 * {@code @Unique(service = UserService.class, field = "email")}
 * public class UserDTO {
 *     private String email;
 *     // ...
 * }
 * </pre>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * 
 * @see dev.forgepack.library.internal.validator.UniqueValidator
 * @see dev.forgepack.library.api.validator.UniqueCheckable
 */

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
