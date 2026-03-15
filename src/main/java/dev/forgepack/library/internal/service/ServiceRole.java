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
 * Serviço responsável pelo gerenciamento de privilégios.
 * <p>
 * Esta classe implementa as operações CRUD para a entidade Role e fornece
 * funcionalidades de verificação de unicidade necessárias para validação.
 *
 * <h3>Funcionalidades principais:</h3>
 * <ul>
 *     <li>Operações CRUD completas para privilégios</li>
 *     <li>Verificação de unicidade de nome</li>
 *     <li>Consultas paginadas e filtradas</li>
 *     <li>Integração com sistema de validação</li>
 * </ul>
 *
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * Website: www.forgepack.dev
 *
 * @see ServiceInterface
 * @see UniqueCheckable
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
    @Override
    public boolean existsByField(String field, Object value) {
        if ("name".equals(field)) {
            return repositoryRole.existsByNameIgnoreCase((String) value);
        }
        else {
            throw new IllegalArgumentException("Invalid argument");
        }
    }
    @Override
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("name".equals(field)){
            return repositoryRole.existsByNameIgnoreCaseAndIdNot(field, id);
        } else {
            throw new IllegalArgumentException("Field must not be null or empty.");
        }
    }
}
