package dev.forgepack.library.internal.repository;

import dev.forgepack.library.api.repository.RepositoryWithName;
import dev.forgepack.library.internal.model.Role;
import java.util.UUID;

/**
 * Implements Role Repository
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public interface RepositoryRole extends RepositoryWithName<Role> {

    Role findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String value, UUID id);
    boolean existsByNameIgnoreCase(String value);
}
