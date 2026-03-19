package dev.forgepack.library.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Set;
import java.util.UUID;

/**
 * Interface base para repositórios da biblioteca ForgePack.
 * <p>
 * Esta interface estende {@link JpaRepository} fornecendo métodos adicionais
 * comuns para operações com entidades que possuem campos de nome e suporte
 * a consultas personalizadas com paginação.
 *
 * Operações suportadas:
 * <ul>
 *     <li>Busca por nome exato e ignore case</li>
 *     <li>Verificação de existência com exclusão por ID</li>
 *     <li>Consultas paginadas e ordenadas</li>
 *     <li>Busca com filtros que ignoram case</li>
 * </ul>
 *
 * @param <T> tipo da entidade gerenciada pelo repositório
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 */

@NoRepositoryBean
public interface RepositoryWithName<T> extends RepositoryInterface<T> {

    Set<T> findByName(String name);
    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
    Page<T> findByNameContainingIgnoreCaseOrderByNameAsc(Pageable pageable, String name);
}
