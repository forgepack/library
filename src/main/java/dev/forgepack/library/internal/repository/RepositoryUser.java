package dev.forgepack.library.internal.repository;

import dev.forgepack.library.internal.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Implements User Repository
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public interface RepositoryUser extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String name);
    boolean existsByUsername(String name);
    boolean existsByUsernameIgnoreCase(String name);
    boolean existsByUsernameIgnoreCaseAndIdNot(String name, UUID id);
    Page<User> findByUsernameContainingIgnoreCaseOrderByUsernameAsc(Pageable pageable, String name);
    Page<User> findById(UUID uuid, Pageable pageable);
    Page<User> findByIdOrderByIdAsc(UUID id, Pageable pageable);
}

