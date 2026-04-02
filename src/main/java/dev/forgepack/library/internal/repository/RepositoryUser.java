package dev.forgepack.library.internal.repository;

import dev.forgepack.library.internal.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import dev.forgepack.library.api.repository.RepositoryGeneric;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryUser extends RepositoryGeneric<User> {

    User findByUsernameIgnoreCase(String name);
    Optional<User> findByUsername(String name);
    boolean existsByUsername(String name);
    boolean existsByUsernameIgnoreCase(String name);
    boolean existsByUsernameIgnoreCaseAndIdNot(String name, UUID id);
    Page<User> findByUsernameContainingIgnoreCaseOrderByUsernameAsc(Pageable pageable, String name);
}

