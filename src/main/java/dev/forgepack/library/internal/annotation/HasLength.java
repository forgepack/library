package dev.forgepack.library.internal.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;
import static dev.forgepack.library.internal.Validator.hasLength;

/**
 * Anotação de validação que verifica se uma string possui comprimento mínimo.
 * <p>
 * Esta anotação utiliza a classe {@link dev.forgepack.library.internal.Validator}
 * para verificar se o valor da string possui pelo menos 8 caracteres.
 * 
 * Configuração:
 * <ul>
 *     <li>Comprimento mínimo: 8 caracteres (fixo)</li>
 *     <li>Aplicação: apenas em campos (FIELD)</li>
 *     <li>Mensagem padrão: "{has.length}"</li>
 * </ul>
 * 
 * Exemplo de uso:
 * <pre>
 * {@code @HasLength}
 * private String password;
 * </pre>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * 
 * @see dev.forgepack.library.internal.Validator#hasLength(int, String)
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HasLength.ValidatorHasLength.class })
@Documented
public @interface HasLength {

    /**
     * Mensagem de erro a ser exibida quando a validação falhar.
     * 
     * @return mensagem de erro padrão
     */
    String message() default "{has.length}";
    
    /**
     * Grupos de validação aos quais esta constraint pertence.
     * 
     * @return grupos de validação
     */
    Class<?>[] groups() default { };
    
    /**
     * Payload personalizado para esta constraint.
     * 
     * @return payload da constraint
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * Validador interno que implementa a lógica de verificação de comprimento mínimo.
     * <p>
     * Esta classe interna implementa a interface {@link ConstraintValidator}
     * para realizar a validação de comprimento mínimo de strings.
     */
    class ValidatorHasLength implements ConstraintValidator<HasLength, String> {

        /**
         * Valida se a string possui o comprimento mínimo requerido.
         * 
         * @param value string a ser validada
         * @param context contexto da validação
         * @return true se a string possui pelo menos 8 caracteres, false caso contrário
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return hasLength(8, value);
        }
    }
}
