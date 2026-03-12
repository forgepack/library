package dev.forgepack.library.internal.repository;

import dev.forgepack.library.internal.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Implements User Repository
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public interface RepositoryUser extends JpaRepository<User, UUID> {

    Page<User> findByUsername(String name);
    boolean existsByUsernameIgnoreCaseAndIdNot(String value, UUID id);
    boolean existsByUsernameIgnoreCase(String value);
}
