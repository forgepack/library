package dev.forgepack.library.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.forgepack.dev
 **/

@NoRepositoryBean
public abstract interface Repository<T> extends JpaRepository<T, UUID> {

    T findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
    Page<T> findById(Pageable pageable, UUID uuid);
    Page<T> findByIdOrderByIdAsc(Pageable pageable, UUID id);
    Page<T> findByNameContainingIgnoreCaseOrderByNameAsc(Pageable pageable, String name);
}
