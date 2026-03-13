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
import static dev.forgepack.library.internal.validator.Validator.hasDigit;

/**
 * Anotação de validação que verifica se uma string contém pelo menos um dígito numérico.
 * <p>
 * Esta anotação utiliza a classe {@link Validator}
 * para verificar se o valor da string possui pelo menos um caractere numérico (0-9).
 * 
 * Configuração:
 * <ul>
 *     <li>Validação: presença de pelo menos um dígito</li>
 *     <li>Aplicação: apenas em campos (FIELD)</li>
 *     <li>Mensagem padrão: "{has.digit}"</li>
 * </ul>
 * 
 * Exemplo de uso:
 * <pre>
 * {@code @HasDigit}
 * private String password;
 * </pre>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * 
 * @see Validator#hasDigit(String)
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HasDigit.ValidatorHasDigit.class })
@Documented
public @interface HasDigit {

    /**
     * Mensagem de erro a ser exibida quando a validação falhar.
     * 
     * @return mensagem de erro padrão
     */
    String message() default "{has.digit}";
    
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
     * Validador interno que implementa a lógica de verificação de dígitos.
     * <p>
     * Esta classe interna implementa a interface {@link ConstraintValidator}
     * para realizar a validação da presença de pelo menos um dígito numérico.
     */
    class ValidatorHasDigit implements ConstraintValidator<HasDigit, String> {

        /**
         * Valida se a string contém pelo menos um dígito numérico.
         * 
         * @param value string a ser validada
         * @param context contexto da validação
         * @return true se a string contém pelo menos um dígito, false caso contrário
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return hasDigit(value);
        }
    }
}
