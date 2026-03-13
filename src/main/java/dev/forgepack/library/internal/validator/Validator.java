package dev.forgepack.library.internal.validator;

/**
 * Classe utilitária com métodos estáticos para validações comuns.
 * <p>
 * Esta classe oferece um conjunto de métodos de validação frequentemente utilizados
 * para verificar propriedades de objetos e strings, como presença de caracteres
 * específicos, comprimento mínimo e valores nulos.
 * 
 * Validações disponíveis:
 * <ul>
 *     <li>Verificação de valores nulos</li>
 *     <li>Presença de dígitos em strings</li>
 *     <li>Presença de letras em strings</li>
 *     <li>Presença de caracteres minúsculos</li>
 *     <li>Presença de caracteres maiúsculos</li>
 *     <li>Verificação de comprimento mínimo</li>
 * </ul>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 */

public final class Validator {

    /**
     * Verifica se o valor fornecido é nulo.
     * 
     * @param value o objeto a ser verificado
     * @return true se o valor for nulo, false caso contrário
     */
    public static boolean isNull(Object value) {
        return value == null;
    }
    
    /**
     * Verifica se a string contém pelo menos um dígito numérico.
     * 
     * @param value a string a ser verificada
     * @return true se a string contém pelo menos um dígito, false caso contrário
     */
    public static boolean hasDigit(String value) {
        return !isNull(value) && value.chars().anyMatch(Character::isDigit);
    }
    
    /**
     * Verifica se a string contém pelo menos uma letra.
     * 
     * @param value a string a ser verificada
     * @return true se a string contém pelo menos uma letra, false caso contrário
     */
    public static boolean hasLetter(String value) {
        return !isNull(value) && value.chars().anyMatch(Character::isLetter);
    }
    
    /**
     * Verifica se a string contém pelo menos um caractere minúsculo.
     * 
     * @param value a string a ser verificada
     * @return true se a string contém pelo menos um caractere minúsculo, false caso contrário
     */
    public static boolean hasLowerCase(String value) {
        return !isNull(value) && value.chars().anyMatch(Character::isLowerCase);
    }
    
    /**
     * Verifica se a string contém pelo menos um caractere maiúsculo.
     * 
     * @param value a string a ser verificada
     * @return true se a string contém pelo menos um caractere maiúsculo, false caso contrário
     */
    public static boolean hasUpperCase(String value) {
        return !isNull(value) && value.chars().anyMatch(Character::isUpperCase);
    }
    
    /**
     * Verifica se a string possui pelo menos o comprimento mínimo especificado.
     * 
     * @param length comprimento mínimo requerido
     * @param value a string a ser verificada
     * @return true se a string possui pelo menos o comprimento especificado, false caso contrário
     */
    public static boolean hasLength(int length, String value) {
        return !isNull(value) && value.length() >= length;
    }
}
