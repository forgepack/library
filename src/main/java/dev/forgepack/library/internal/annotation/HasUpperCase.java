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
import static dev.forgepack.library.internal.Validator.hasUpperCase;

/**
 * Anotação de validação que verifica se uma string contém pelo menos um caractere maiúsculo.
 * <p>
 * Esta anotação utiliza a classe {@link dev.forgepack.library.internal.Validator}
 * para verificar se o valor da string possui pelo menos um caractere em maiúsculo (A-Z).
 * 
 * Configuração:
 * <ul>
 *     <li>Validação: presença de pelo menos um caractere maiúsculo</li>
 *     <li>Aplicação: apenas em campos (FIELD)</li>
 *     <li>Mensagem padrão: "{has.upper.case}"</li>
 * </ul>
 * 
 * Exemplo de uso:
 * <pre>
 * {@code @HasUpperCase}
 * private String password;
 * </pre>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * 
 * @see dev.forgepack.library.internal.Validator#hasUpperCase(String)
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HasUpperCase.ValidatorHasUpperCase.class })
@Documented
public @interface HasUpperCase {

    /**
     * Mensagem de erro a ser exibida quando a validação falhar.
     * 
     * @return mensagem de erro padrão
     */
    String message() default "{has.upper.case}";
    
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
     * Validador interno que implementa a lógica de verificação de caracteres maiúsculos.
     * <p>
     * Esta classe interna implementa a interface {@link ConstraintValidator}
     * para realizar a validação da presença de pelo menos um caractere maiúsculo.
     */
    class ValidatorHasUpperCase implements ConstraintValidator<HasUpperCase, String> {

        /**
         * Valida se a string contém pelo menos um caractere maiúsculo.
         * 
         * @param value string a ser validada
         * @param context contexto da validação
         * @return true se a string contém pelo menos um caractere maiúsculo, false caso contrário
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return hasUpperCase(value);
        }
    }
}
