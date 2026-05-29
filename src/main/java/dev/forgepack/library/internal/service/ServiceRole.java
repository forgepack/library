package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.api.service.ServiceUniqueCheckable;
import dev.forgepack.library.api.repository.RepositoryGeneric;
import dev.forgepack.library.internal.model.Role;
import dev.forgepack.library.internal.payload.DTORequestRole;
import dev.forgepack.library.internal.payload.DTOResponseRole;
import dev.forgepack.library.internal.repository.RepositoryRole;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ServiceRole extends ServiceGenericImpl<Role, DTORequestRole, DTOResponseRole> implements ServiceUniqueCheckable {

    private final RepositoryRole repositoryRole;

    public ServiceRole(RepositoryGeneric<Role> repositoryGeneric, Mapper<Role, DTORequestRole, DTOResponseRole> mapperInterface, RepositoryRole repositoryRole) {
        super(Role.class, repositoryGeneric, mapperInterface);
        this.repositoryRole = repositoryRole;
    }

    @Override
    @Transactional
    public boolean existsByField(String field, Object value) {
        if ("name".equals(field)) {
            return repositoryRole.existsByNameIgnoreCase((String) value);
        }
        else {
            throw new IllegalArgumentException("Invalid argument");
        }
    }

    @Override
    @Transactional
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("name".equals(field)){
            return repositoryRole.existsByNameIgnoreCaseAndIdNot((String) value, id);
        } else {
            throw new IllegalArgumentException("Field must not be null or empty.");
        }
    }
}
