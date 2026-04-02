package dev.forgepack.library.internal.repository;

import dev.forgepack.library.api.repository.RepositoryGenericWithName;
import dev.forgepack.library.internal.model.Role;
import java.util.UUID;

public interface RepositoryRole extends RepositoryGenericWithName<Role> {

    Role findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String value, UUID id);
    boolean existsByNameIgnoreCase(String value);
}
