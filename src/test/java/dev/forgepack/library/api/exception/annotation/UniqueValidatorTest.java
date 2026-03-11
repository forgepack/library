package dev.forgepack.library.api.exception.annotation;

import dev.forgepack.library.api.annotation.Unique;
import dev.forgepack.library.internal.validator.UniqueValidator;
import dev.forgepack.library.internal.service.ServiceRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import java.lang.reflect.Field;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Validator Unique
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

@ExtendWith(MockitoExtension.class)
class UniqueValidatorTest {

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private ServiceRole serviceRole;

    private UniqueValidator validator;
    private jakarta.validation.ConstraintValidatorContext ctx;

    // DTO auxiliar para testes isolados
    @Unique(service = ServiceRole.class, field = "name")
    record TestDTO(UUID id, String name) {}

    // DTO com idField como String UUID
    @Unique(service = ServiceRole.class, field = "name", idField = "id")
    record TestDTOStringId(String id, String name) {}

    @BeforeEach
    void setUp() throws Exception {
        validator = new UniqueValidator(applicationContext);
        initValidator(validator, "name", "id");

        when(applicationContext.getBean(ServiceRole.class)).thenReturn(serviceRole);

        ctx = mock(jakarta.validation.ConstraintValidatorContext.class);
        var builder = mock(jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder.class);
        var nodeBuilder = mock(
                jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);

        when(ctx.getDefaultConstraintMessageTemplate()).thenReturn("already exists: {label}");
        when(ctx.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
        when(builder.addPropertyNode(any())).thenReturn(nodeBuilder);
    }

    // --- utilidade para inicializar via reflexão ---
    private void initValidator(UniqueValidator v, String field, String idField) throws Exception {
        setField(v, "fieldName", field);
        setField(v, "idFieldName", idField);
        setField(v, "serviceClass", ServiceRole.class);
        setField(v, "service", null); // força lazy resolve
    }

    private void setField(Object target, String name, Object value) throws Exception {
        Field f = UniqueValidator.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(target, value);
    }

    // =====================================================================
    // Casos: valor null / campo null / campo em branco
    // =====================================================================

//    @Test
//    @DisplayName("Retorna true quando o objeto é null")
//    void nullObject_returnsTrue() {
//        assertThat(validator.isValid(null, ctx)).isTrue();
//        verifyNoInteractions(serviceRole);
//    }
//
//    @Test
//    @DisplayName("Retorna true quando fieldValue é null")
//    void nullFieldValue_returnsTrue() {
//        var dto = new TestDTO(null, null);
//        assertThat(validator.isValid(dto, ctx)).isTrue();
//        verifyNoInteractions(serviceRole);
//    }
//
//    @Test
//    @DisplayName("Retorna true quando fieldValue é blank")
//    void blankFieldValue_returnsTrue() {
//        var dto = new TestDTO(null, "   ");
//        assertThat(validator.isValid(dto, ctx)).isTrue();
//        verifyNoInteractions(serviceRole);
//    }
//
//    // =====================================================================
//    // Casos: criação (id null) — usa existsByField
//    // =====================================================================
//
//    @Test
//    @DisplayName("Criação: nome único → válido")
//    void creation_uniqueName_valid() {
//        when(serviceRole.existsByField("name", "Admin")).thenReturn(false);
//
//        var dto = new TestDTO(null, "Admin");
//        assertThat(validator.isValid(dto, ctx)).isTrue();
//    }
//
//    @Test
//    @DisplayName("Criação: nome duplicado → inválido")
//    void creation_duplicateName_invalid() {
//        when(serviceRole.existsByField("name", "Admin")).thenReturn(true);
//
//        var dto = new TestDTO(null, "Admin");
//        assertThat(validator.isValid(dto, ctx)).isFalse();
//    }
//
//    @Test
//    @DisplayName("Criação: nome é trimado antes da verificação")
//    void creation_trimsWhitespace() {
//        when(serviceRole.existsByField("name", "Admin")).thenReturn(false);
//
//        var dto = new TestDTO(null, "  Admin  ");
//        assertThat(validator.isValid(dto, ctx)).isTrue();
//
//        verify(serviceRole).existsByField("name", "Admin"); // trimado
//    }
//
//    // =====================================================================
//    // Casos: atualização (id presente) — usa existsByFieldAndIdNot
//    // =====================================================================
//
//    @Test
//    @DisplayName("Atualização: nome único para outro registro → válido")
//    void update_uniqueForOthers_valid() {
//        UUID id = UUID.randomUUID();
//        when(serviceRole.existsByFieldAndIdNot("name", "Admin", id)).thenReturn(false);
//
//        var dto = new TestDTO(id, "Admin");
//        assertThat(validator.isValid(dto, ctx)).isTrue();
//    }
//
//    @Test
//    @DisplayName("Atualização: nome em uso por outro registro → inválido")
//    void update_duplicateForOthers_invalid() {
//        UUID id = UUID.randomUUID();
//        when(serviceRole.existsByFieldAndIdNot("name", "Admin", id)).thenReturn(true);
//
//        var dto = new TestDTO(id, "Admin");
//        assertThat(validator.isValid(dto, ctx)).isFalse();
//    }
//
//    @Test
//    @DisplayName("Atualização: idField como String UUID é convertido corretamente")
//    void update_stringUUID_convertedCorrectly() throws Exception {
//        UUID id = UUID.randomUUID();
//        initValidator(validator, "name", "id");
//
//        var dto = new TestDTOStringId(id.toString(), "Admin");
//        when(serviceRole.existsByFieldAndIdNot("name", "Admin", id)).thenReturn(false);
//
//        assertThat(validator.isValid(dto, ctx)).isTrue();
//    }
//
//    // =====================================================================
//    // Casos de erro
//    // =====================================================================
//
//    @Test
//    @DisplayName("Lança exceção quando campo não existe no DTO")
//    void fieldNotFound_throwsIllegalArgument() throws Exception {
//        initValidator(validator, "nonExistentField", "id");
//        var dto = new TestDTO(null, "Admin");
//
//        assertThatThrownBy(() -> validator.isValid(dto, ctx))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("nonExistentField");
//    }
//
//    @Test
//    @DisplayName("Lança exceção quando idField não é UUID válido")
//    void invalidUUID_throwsIllegalArgument() {
//        record BadDTO(String id, String name) {}
//
//        // precisa de um validator com idField = "id" apontando para campo String
//        var v2 = new UniqueValidator(applicationContext);
//        try {
//            setField(v2, "fieldName", "name");
//            setField(v2, "idFieldName", "id");
//            setField(v2, "serviceClass", ServiceRole.class);
//            setField(v2, "service", serviceRole);
//        } catch (Exception e) { fail("Setup falhou"); }
//
//        var dto = new BadDTO("not-a-uuid", "Admin");
//
//        assertThatThrownBy(() -> v2.isValid(dto, ctx))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("UUID válido");
//    }
//
//    // =====================================================================
//    // Cache de reflexão
//    // =====================================================================
//
//    @Test
//    @DisplayName("Cache de reflexão: múltiplas chamadas não relançam exceção")
//    void reflectionCache_noErrorOnRepeat() {
//        when(serviceRole.existsByField("name", "A")).thenReturn(false);
//        when(serviceRole.existsByField("name", "B")).thenReturn(false);
//
//        assertThatNoException().isThrownBy(() -> {
//            validator.isValid(new TestDTO(null, "A"), ctx);
//            validator.isValid(new TestDTO(null, "B"), ctx);
//        });
//    }
//
//    // =====================================================================
//    // Integração via Bean Validation (Validator padrão)
//    // =====================================================================
//
//    @Nested
//    @DisplayName("Integração Bean Validation — anotações @NotNull / @NotBlank")
//    class BeanValidationIntegration {
//
//        private Validator beanValidator;
//
//        @BeforeEach
//        void setUp() {
//            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//            beanValidator = factory.getValidator();
//        }
//
//        @Test
//        @DisplayName("@NotNull falha quando name é null")
//        void notNull_failsOnNull() {
//            var dto = new DTORequestRole(null, null);
//            Set<ConstraintViolation<DTORequestRole>> violations = beanValidator.validate(dto);
//
//            assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
//        }
//
//        @Test
//        @DisplayName("@NotBlank falha quando name é blank")
//        void notBlank_failsOnBlank() {
//            var dto = new DTORequestRole(null, "  ");
//            Set<ConstraintViolation<DTORequestRole>> violations = beanValidator.validate(dto);
//
//            assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
//        }
//    }
}
