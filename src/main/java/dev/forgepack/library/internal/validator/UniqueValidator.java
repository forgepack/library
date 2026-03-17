package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.annotation.Unique;
import dev.forgepack.library.api.validator.UniqueCheckable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Bean Validation validator responsible for enforcing the {@link Unique} constraint.
 *
 * <p>This validator checks whether the value of a specified field is unique in the
 * persistence layer. The validation logic delegates the uniqueness verification
 * to a service that implements {@link UniqueCheckable}.</p>
 *
 * <p>The validator supports both entity creation and update scenarios:</p>
 * <ul>
 *     <li><b>Create operation</b>: verifies that no record exists with the same field value</li>
 *     <li><b>Update operation</b>: verifies that no other record exists with the same field
 *     value excluding the current entity identifier</li>
 * </ul>
 *
 * <p>The service responsible for performing the uniqueness check is obtained
 * from the Spring {@link ApplicationContext} based on the configuration provided
 * in the {@link Unique} annotation.</p>
 *
 * <p>Field values are accessed via reflection, allowing the validator to read
 * private fields without requiring public getters.</p>
 *
 * <p>Null or blank values are considered valid and will not trigger a uniqueness
 * verification.</p>
 *
 * <h3>Validation flow</h3>
 * <ol>
 *     <li>Retrieve the field value configured in {@link Unique#field()}</li>
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
@Component
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private String field;
    private String idField;
    private Class<? extends UniqueCheckable> serviceClass;
    private UniqueCheckable service;
    private final ApplicationContext context;

    public UniqueValidator(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void initialize(Unique annotation) {
        this.field = annotation.field();
        this.idField = annotation.idField();
        this.serviceClass = annotation.service();
        this.service = (UniqueCheckable) context.getBean(annotation.service());
    }
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;
        Object fieldValue = getFieldValue(value, field);
        if (fieldValue == null) return true;
        String trimmed = fieldValue.toString().trim();
        if (trimmed.isBlank()) return true;
        Object idValue = getFieldValue(value, idField);
        boolean isUnique = (idValue == null || idValue.toString().isBlank())
                ? !resolveService().existsByField(field, trimmed)
                : !resolveService().existsByFieldAndIdNot(field, trimmed, (UUID) idValue);
        if (!isUnique) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            context.getDefaultConstraintMessageTemplate()
                                    .replace("{label}", field)
                    ).addPropertyNode(field)
                    .addConstraintViolation();
        }
        return isUnique;
    }
    private UniqueCheckable resolveService() {
        if (service == null) {
            service = context.getBean(serviceClass);
        }
        return service;
    }
    private Object getFieldValue(Object target, String fieldName) {
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(target);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(
                    "Field '" + fieldName + "' not found in " + target.getClass().getSimpleName()
            );
        }
    }
}
