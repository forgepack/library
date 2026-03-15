package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.api.repository.RepositoryInterface;
import dev.forgepack.library.api.validator.UniqueCheckable;
import dev.forgepack.library.api.service.ServiceInterface;
import dev.forgepack.library.internal.model.Privilege;
import dev.forgepack.library.internal.payload.DTORequestPrivilege;
import dev.forgepack.library.internal.payload.DTOResponsePrivilege;
import dev.forgepack.library.internal.repository.RepositoryPrivilege;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * Serviço responsável pelo gerenciamento de privilégios.
 * <p>
 * Esta classe implementa as operações CRUD para a entidade Privilege e fornece
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
 * @see Privilege
 * @see DTORequestPrivilege
 * @see DTOResponsePrivilege
 */
@Service
public class ServicePrivilege extends ServiceGeneric<Privilege, DTORequestPrivilege, DTOResponsePrivilege> implements UniqueCheckable {

    private final RepositoryPrivilege repositoryPrivilege;

    public ServicePrivilege(RepositoryInterface<Privilege> repositoryInterface, Mapper<Privilege, DTORequestPrivilege, DTOResponsePrivilege> mapperInterface, RepositoryPrivilege repositoryPrivilege) {
        super(Privilege.class, repositoryInterface, mapperInterface);
        this.repositoryPrivilege = repositoryPrivilege;
    }
    @Override
    public boolean existsByField(String field, Object value) {
        if ("name".equals(field)) {
            return repositoryPrivilege.existsByNameIgnoreCase((String) value);
        }
        else {
            throw new IllegalArgumentException("Invalid argument");
        }
    }
    @Override
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("name".equals(field)){
            return repositoryPrivilege.existsByNameIgnoreCaseAndIdNot(field, id);
        } else {
            throw new IllegalArgumentException("Field must not be null or empty.");
        }
    }
}
