package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.annotation.Unique;
import dev.forgepack.library.api.validator.UniqueCheckable;
import dev.forgepack.library.api.validator.UniqueValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Default implementation of {@link UniqueValidator}.
 *
 * <p>This bean is managed by Spring and requires an {@link ApplicationContext}
 * to resolve the configured {@link UniqueCheckable} service at runtime.</p>
 *
 * @see UniqueValidator
 */
@Component
public class ValidatorUnique implements UniqueValidator {

    private String[] fields;
    private String idField;
    private Class<? extends UniqueCheckable> serviceClass;
    private UniqueCheckable service;
    private final ApplicationContext context;

    public ValidatorUnique(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void initialize(Unique annotation) {
        this.fields = annotation.fields();
        this.idField = annotation.idField();
        this.serviceClass = annotation.service();
        this.service = context.getBean(annotation.service());
    }
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;
        Object idValue = getFieldValue(value, idField);
        boolean allUnique = true;
        for (String field : fields) {
            Object fieldValue = getFieldValue(value, field);
            if (fieldValue == null) continue;
            String trimmed = fieldValue.toString().trim();
            if (trimmed.isBlank()) continue;
            boolean isUnique = (idValue == null || idValue.toString().isBlank())
                    ? !resolveService().existsByField(field, trimmed)
                    : !resolveService().existsByFieldAndIdNot(field, trimmed, (UUID) idValue);
            if (!isUnique) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                context.getDefaultConstraintMessageTemplate()
                                        .replace("{field}", field)
                        ).addPropertyNode(field)
                        .addConstraintViolation();
                allUnique = false;
            }
        }
        return allUnique;
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
