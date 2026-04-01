package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.api.validator.UniqueCheckable;
import dev.forgepack.library.api.repository.RepositoryInterface;
import dev.forgepack.library.api.service.ServiceInterface;
import dev.forgepack.library.internal.model.Role;
import dev.forgepack.library.internal.payload.DTORequestRole;
import dev.forgepack.library.internal.payload.DTOResponseRole;
import dev.forgepack.library.internal.repository.RepositoryRole;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * Service responsible for managing {@link Role} entities.
 *
 * <p>Extends {@link ServiceGeneric} by inheriting the full CRUD operations
 * for the entity {@link Role}, and implements {@link UniqueCheckable}
 * to provide uniqueness checks used during data validation.</p>
 *
 * <h3>Main responsibilities:</h3>
 * <ul>
 *     <li>Provide CRUD operations for {@link Role}</li>
 *     <li>Validate uniqueness constraints for role attributes</li>
 *     <li>Support paginated and filtered queries</li>
 *     <li>Integrate entity–DTO mapping through {@link Mapper}</li>
 * </ul>
 *
 * <h3>Supported uniqueness fields</h3>
 * <ul>
 *     <li>{@code name} - Checks for duplicates, ignoring case.</li>
 * </ul>
 *
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 *
 * @see ServiceGeneric
 * @see ServiceInterface
 * @see UniqueCheckable
 * @see RepositoryRole
 * @see Role
 * @see DTORequestRole
 * @see DTOResponseRole
 */
@Service
public class ServiceRole extends ServiceGeneric<Role, DTORequestRole, DTOResponseRole> implements UniqueCheckable {

    private final RepositoryRole repositoryRole;

    public ServiceRole(RepositoryInterface<Role> repositoryInterface, Mapper<Role, DTORequestRole, DTOResponseRole> mapperInterface, RepositoryRole repositoryRole) {
        super(Role.class, repositoryInterface, mapperInterface);
        this.repositoryRole = repositoryRole;
    }
    /**
     * Checks if a record exists with the specified value in a given field.
     *
     * @param field {@link String} name of the field to be checked.
     * @param value {@link Object} The value that will be compared in the specified field.
     * @return  {@code true} if another record exists with the same value in the specified field;
     *          {@code false} otherwise.
     * @throws IllegalArgumentException if the specified field is not supported.
     * @author Marcelo Ribeiro Gadelha
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * boolean exists = service.existsByField("name", "Admin");
     * }</pre>
     */
    @Override
    public boolean existsByField(String field, Object value) {
        if ("name".equals(field)) {
            return repositoryRole.existsByNameIgnoreCase((String) value);
        }
        else {
            throw new IllegalArgumentException("Invalid argument");
        }
    }
    /**
     * Checks if a record exists with the specified value in a given field, excluding the record with the given {@code id} from the check.
     *
     * @param field {@link String} name of the field to be checked.
     * @param value {@link Object} The value that will be compared in the specified field.
     * @param id {@link UUID} Identifier of the record that should be ignored in the verification.
     * @return  {@code true} if another record exists with the same value in the specified field;
     *          {@code false} otherwise.
     * @throws IllegalArgumentException if the specified field is not supported.
     * @author Marcelo Ribeiro Gadelha
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * boolean exists = service.existsByFieldAndIdNot("name", "Admin", someUUID);
     * }</pre>
     */
    @Override
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("name".equals(field)){
            return repositoryRole.existsByNameIgnoreCaseAndIdNot((String) value, id);
        } else {
            throw new IllegalArgumentException("Field must not be null or empty.");
        }
    }
}
