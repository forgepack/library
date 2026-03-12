package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.api.validator.UniqueCheckable;
import dev.forgepack.library.api.repository.Repository;
import dev.forgepack.library.api.service.ServiceInterface;
import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.internal.payload.DTORequestUser;
import dev.forgepack.library.internal.payload.DTOResponseUser;
import dev.forgepack.library.internal.repository.RepositoryUser;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * Serviço responsável pelo gerenciamento de privilégios.
 * <p>
 * Esta classe implementa as operações CRUD para a entidade User e fornece
 * funcionalidades de verificação de unicidade necessárias para validação.
 *
 * <h3>Funcionalidades principais:</h3>
 * <ul>
 *     <li>Operações CRUD completas para privilégios</li>
 *     <li>Verificação de unicidade de username</li>
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
 * @see User
 * @see DTORequestUser
 * @see DTOResponseUser
 */
@Service
public class ServiceUser extends ServiceGeneric<User, DTORequestUser, DTOResponseUser> implements UniqueCheckable {

    private final RepositoryUser repositoryUser;

    public ServiceUser(Repository<User> repository, Mapper<User, DTORequestUser, DTOResponseUser> mapperInterface, RepositoryUser repositoryUser) {
        super(User.class, repository, mapperInterface);
        this.repositoryUser = repositoryUser;
    }
    @Override
    public boolean existsByField(String field, Object value) {
        if ("username".equals(field)) {
            return repositoryUser.existsByUsernameIgnoreCase((String) value);
        }
        else {
            throw new IllegalArgumentException("Invalid argument");
        }
    }
    @Override
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("username".equals(field)){
            return repositoryUser.existsByUsernameIgnoreCaseAndIdNot(field, id);
        } else {
            throw new IllegalArgumentException("Field must not be null or empty.");
        }
    }
}
