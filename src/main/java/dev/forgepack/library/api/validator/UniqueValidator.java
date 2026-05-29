package dev.forgepack.library.api.validator;

import dev.forgepack.library.api.annotation.Unique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Contract for validators that enforce the {@link Unique} constraint.
 *
 * <p>Implementations are responsible for verifying that the values of configured
 * fields are unique in the persistence layer. The validation logic must delegate
 * uniqueness checks to a service that implements {@link UniqueCheckable}.</p>
 *
 * <p>Both entity creation and update scenarios must be supported:</p>
 * <ul>
 *     <li><b>Create operation</b>: verifies that no record exists with the same field value</li>
 *     <li><b>Update operation</b>: verifies that no other record exists with the same field
 *     value, excluding the current entity identifier</li>
 * </ul>
 *
 * <p>Null or blank values are considered valid and must not trigger a uniqueness
 * verification.</p>
 *
 * <h3>Validation flow</h3>
 * <ol>
 *     <li>Retrieve the field value configured in {@link Unique#fields()}</li>
 *     <li>If present, retrieve the identifier configured in {@link Unique#idField()}</li>
 *     <li>Resolve the configured {@link UniqueCheckable} service</li>
 *     <li>Execute the appropriate uniqueness verification</li>
 * </ol>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 *
 * @see Unique
 * @see UniqueCheckable
 * @see ConstraintValidator
 */
public interface UniqueValidator extends ConstraintValidator<Unique, Object> {

    /**
     * Initializes the validator with the metadata provided in the {@link Unique} annotation.
     *
     * @param annotation the annotation instance containing the constraint configuration
     */
    void initialize(Unique annotation);

    /**
     * Validates whether all configured fields of the given object satisfy the uniqueness constraint.
     *
     * <p>Null objects are considered valid. For each configured field, blank or null values are
     * skipped. On a uniqueness violation, a constraint violation is added to the specific field
     * node instead of using the default message template.</p>
     *
     * @param value   the object being validated
     * @param context the constraint validator context
     * @return {@code true} if all configured fields are unique; {@code false} otherwise
     */
    boolean isValid(Object value, ConstraintValidatorContext context);
}
