package dev.forgepack.library.internal.validator;

import dev.forgepack.library.api.annotation.Unique;
import dev.forgepack.library.api.service.ServiceUniqueCheckable;
import dev.forgepack.library.api.validator.ValidatorUnique;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.UUID;

@Component
public class ValidatorUniqueImpl implements ValidatorUnique {

    private String[] fields;
    private String idField;
    private Class<? extends ServiceUniqueCheckable> serviceClass;
    private ServiceUniqueCheckable service;
    private final ApplicationContext context;

    public ValidatorUniqueImpl(ApplicationContext context) {
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
    private ServiceUniqueCheckable resolveService() {
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
