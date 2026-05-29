package dev.forgepack.library.api.validator;

/**
 * Utility class providing common validation helper methods.
 *
 * <p>This class contains a set of static methods used to validate
 * common properties of objects and strings, such as null checks,
 * character presence, and minimum length requirements.</p>
 *
 * <p>The methods are designed to be lightweight and reusable across
 * validation components within the library.</p>
 *
 * <h3>Available validations</h3>
 * <ul>
 *     <li>Null value verification</li>
 *     <li>Digit presence in strings</li>
 *     <li>Letter presence in strings</li>
 *     <li>Lowercase character presence</li>
 *     <li>Uppercase character presence</li>
 *     <li>Minimum length verification</li>
 * </ul>
 *
 * <p>This class cannot be instantiated.</p>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 */
public interface Validator {

    /**
     * Checks whether the provided value is {@code null}.
     *
     * @param value object to be checked
     * @return {@code true} if the value is {@code null}; {@code false} otherwise
     */
    boolean isNull(Object value);

    /**
     * Checks whether the given string contains at least one numeric digit.
     *
     * @param value string to be evaluated
     * @return {@code true} if the string contains a digit; {@code false} otherwise
     */
    boolean hasDigit(String value);

    /**
     * Checks whether the given string contains at least one letter.
     *
     * @param value string to be evaluated
     * @return {@code true} if the string contains a letter; {@code false} otherwise
     */
    boolean hasLetter(String value);

    /**
     * Checks whether the given string contains at least one lowercase character.
     *
     * @param value string to be evaluated
     * @return {@code true} if the string contains a lowercase character; {@code false} otherwise
     */
    boolean hasLowerCase(String value);

    /**
     * Checks whether the given string contains at least one uppercase character.
     *
     * @param value string to be evaluated
     * @return {@code true} if the string contains an uppercase character; {@code false} otherwise
     */
    boolean hasUpperCase(String value);

    /**
     * Checks whether the given string has at least the specified minimum length.
     *
     * @param length minimum required length
     * @param value string to be evaluated
     * @return {@code true} if the string length is greater than or equal to
     *         the specified length; {@code false} otherwise
     */
    boolean hasLength(int length, String value);
}
