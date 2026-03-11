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
 * Validador customizado para verificação de unicidade de campos.
 * <p>
 * Esta classe implementa a lógica de validação para a anotação {@code @Unique},
 * verificando se um campo específico possui valor único na base de dados.
 * Suporta tanto validação para criação quanto para atualização de entidades.
 * 
 * <h3>Funcionalidades:</h3>
 * <ul>
 *     <li>Validação de unicidade em criação de registros</li>
 *     <li>Validação de unicidade em atualização (excluindo o próprio registro)</li>
 *     <li>Integração com Spring Context para injeção de dependências</li>
 *     <li>Acesso reflexivo a campos privados</li>
 *     <li>Tratamento de valores nulos e em branco</li>
 * </ul>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * Website: www.forgepack.dev
 * 
 * @see dev.forgepack.library.api.annotation.Unique
 * @see dev.forgepack.library.api.validator.UniqueCheckable
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
