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
import static dev.forgepack.library.internal.Validator.hasLetter;

/**
 * Anotação de validação que verifica se uma string contém pelo menos uma letra.
 * <p>
 * Esta anotação utiliza a classe {@link dev.forgepack.library.internal.Validator}
 * para verificar se o valor da string possui pelo menos um caractere alfabético (a-z ou A-Z).
 * 
 * Configuração:
 * <ul>
 *     <li>Validação: presença de pelo menos uma letra</li>
 *     <li>Aplicação: apenas em campos (FIELD)</li>
 *     <li>Mensagem padrão: "{has.letter}"</li>
 * </ul>
 * 
 * Exemplo de uso:
 * <pre>
 * {@code @HasLetter}
 * private String password;
 * </pre>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * 
 * @see dev.forgepack.library.internal.Validator#hasLetter(String)
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HasLetter.ValidatorHasLetter.class })
@Documented
public @interface HasLetter {

    /**
     * Mensagem de erro a ser exibida quando a validação falhar.
     * 
     * @return mensagem de erro padrão
     */
    String message() default "{has.letter}";
    
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
     * Validador interno que implementa a lógica de verificação de letras.
     * <p>
     * Esta classe interna implementa a interface {@link ConstraintValidator}
     * para realizar a validação da presença de pelo menos uma letra.
     */
    class ValidatorHasLetter implements ConstraintValidator<HasLetter, String> {

        /**
         * Valida se a string contém pelo menos uma letra.
         * 
         * @param value string a ser validada
         * @param context contexto da validação
         * @return true se a string contém pelo menos uma letra, false caso contrário
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return hasLetter(value);
        }
    }
}
