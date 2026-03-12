package dev.forgepack.library.internal.repository;

import dev.forgepack.library.api.repository.Repository;
import dev.forgepack.library.internal.model.Privilege;
import org.springframework.data.domain.Page;
import java.util.UUID;

/**
 * Implements Privilege Repository
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public interface RepositoryPrivilege extends Repository<Privilege> {

    Page<Privilege> findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String value, UUID id);
    boolean existsByNameIgnoreCase(String value);
}
