package dev.forgepack.library.internal.repository;

import dev.forgepack.library.api.repository.Repository;
import dev.forgepack.library.internal.model.Role;
import org.springframework.data.domain.Page;
import java.util.UUID;

/**
 * Implements Role Repository
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public interface RepositoryRole extends Repository<Role> {

    Page<Role> findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String value, UUID id);
    boolean existsByNameIgnoreCase(String value);
}
