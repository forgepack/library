package dev.forgepack.library.internal.repository;

import dev.forgepack.library.api.repository.RepositoryInterface;
import dev.forgepack.library.internal.model.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryToken extends RepositoryInterface<Token> {

    Page<Token> findById(Pageable pageable, UUID uuid);
    Optional<Token> findByRefreshToken(UUID uuid);
    boolean existsByRefreshToken(UUID refreshToken);
}
