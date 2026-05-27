package dev.forgepack.library.internal.repository;

import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.api.repository.RepositoryGeneric;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryUser extends RepositoryGeneric<User> {

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.role r LEFT JOIN FETCH r.privilege WHERE u.username = :name")
    Optional<User> findByUsername(String name);
    boolean existsByUsernameIgnoreCase(String name);
    boolean existsByUsernameIgnoreCaseAndIdNot(String name, UUID id);
}

