package dev.forgepack.library.api.exception.annotation;

import dev.forgepack.library.api.exception.UniqueCheckable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Validator Unique
 *
 * @author	Marcelo Ribeiro Gadelha
 * Email:	gadelha.ti@gmail.com
 * Website:	www.forgepack.dev
 **/

@Component
class UniqueValidator implements ConstraintValidator<Unique, Object> {

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
                    "Campo '" + fieldName + "' não encontrado em " + target.getClass().getSimpleName()
            );
        }
    }
}
