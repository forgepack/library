package dev.forgepack.library.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface Service
 *
 * @author	Marcelo Ribeiro Gadelha
 * Email:	gadelha.ti@gmail.com
 * Website:	www.forgepack.dev
 **/

public interface ServiceInterface<T, I, O> {
    O create(I created);
    Page<O> retrieve(Pageable pageable, String value, Class<T> entityClass);
    O retrieve(UUID id);
    O update(UUID id, I updated);
    O delete(UUID id);
}
