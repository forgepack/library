package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.api.repository.RepositoryGeneric;
import dev.forgepack.library.api.service.ServiceUniqueCheckable;
import dev.forgepack.library.internal.model.Privilege;
import dev.forgepack.library.internal.payload.DTORequestPrivilege;
import dev.forgepack.library.internal.payload.DTOResponsePrivilege;
import dev.forgepack.library.internal.repository.RepositoryPrivilege;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ServicePrivilege extends ServiceGenericImpl<Privilege, DTORequestPrivilege, DTOResponsePrivilege> implements ServiceUniqueCheckable {

    private final RepositoryPrivilege repositoryPrivilege;

    public ServicePrivilege(RepositoryGeneric<Privilege> repositoryGeneric, Mapper<Privilege, DTORequestPrivilege, DTOResponsePrivilege> mapperInterface, RepositoryPrivilege repositoryPrivilege) {
        super(Privilege.class, repositoryGeneric, mapperInterface);
        this.repositoryPrivilege = repositoryPrivilege;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByField(String field, Object value) {
        if ("name".equals(field)) {
            return repositoryPrivilege.existsByNameIgnoreCase((String) value);
        }
        else {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("name".equals(field)){
            return repositoryPrivilege.existsByNameIgnoreCaseAndIdNot((String) value, id);
        } else {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }
    }
}
