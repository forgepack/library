package dev.forgepack.library.internal.repository;

import dev.forgepack.library.api.repository.RepositoryGenericWithName;
import dev.forgepack.library.internal.model.Privilege;
import java.util.UUID;

public interface RepositoryPrivilege extends RepositoryGenericWithName<Privilege> {

    Privilege findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String value, UUID id);
    boolean existsByNameIgnoreCase(String value);
}
