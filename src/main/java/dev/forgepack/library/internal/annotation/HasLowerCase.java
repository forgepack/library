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
 * Anotação de validação que verifica se uma string contém pelo menos um caractere minúsculo.
 * <p>
 * Esta anotação utiliza a classe {@link Validator}
 * para verificar se o valor da string possui pelo menos um caractere em minúsculo (a-z).
 * 
 * Configuração:
 * <ul>
 *     <li>Validação: presença de pelo menos um caractere minúsculo</li>
 *     <li>Aplicação: apenas em campos (FIELD)</li>
 *     <li>Mensagem padrão: "{has.lower.case}"</li>
 * </ul>
 * 
 * Exemplo de uso:
 * <pre>
 * {@code @HasLowerCase}
 * private String password;
 * </pre>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * 
 * @see Validator#hasLowerCase(String)
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HasLowerCase.ValidatorHasLowerCase.class })
@Documented
public @interface HasLowerCase {

    /**
     * Mensagem de erro a ser exibida quando a validação falhar.
     * 
     * @return mensagem de erro padrão
     */
    String message() default "{has.lower.case}";
    
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
     * Validador interno que implementa a lógica de verificação de caracteres minúsculos.
     * <p>
     * Esta classe interna implementa a interface {@link ConstraintValidator}
     * para realizar a validação da presença de pelo menos um caractere minúsculo.
     */
    class ValidatorHasLowerCase implements ConstraintValidator<HasLowerCase, String> {

        /**
         * Valida se a string contém pelo menos um caractere minúsculo.
         * 
         * @param value string a ser validada
         * @param context contexto da validação
         * @return true se a string contém pelo menos um caractere minúsculo, false caso contrário
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return hasLowerCase(value);
        }
    }
}
