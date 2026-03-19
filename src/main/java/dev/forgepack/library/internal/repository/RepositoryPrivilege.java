package dev.forgepack.library.internal.repository;

import dev.forgepack.library.api.repository.RepositoryWithName;
import dev.forgepack.library.internal.model.Privilege;
import java.util.Set;
import java.util.UUID;

/**
 * Implements Privilege Repository
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public interface RepositoryPrivilege extends RepositoryWithName<Privilege> {

    Set<Privilege> findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String value, UUID id);
    boolean existsByNameIgnoreCase(String value);
}
