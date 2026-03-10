package dev.forgepack.library.api.service;

import dev.forgepack.library.api.exception.UniqueCheckable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implements Interface Service
 *
 * @author	Marcelo Ribeiro Gadelha
 * Email:	gadelha.ti@gmail.com
 * Website:	www.forgepack.dev
 **/

@Service
public class ServiceRole implements UniqueCheckable {

    @Override
    public boolean existsByField(String field, Object value) {
        if ("name".equals(field)) {
            return existsByName((String) value);
        }
        throw new IllegalArgumentException("Field not supported");
    }
    @Override
    public boolean existsByFieldAndIdNot(String field, Object value, UUID id) {
        if ("name".equals(field)) {
            return existsByNameAndIdNot((String) value, id);
        }
        throw new IllegalArgumentException("Field not supported");
    }

    public boolean existsByName(String name) {
        // repository.existsByName(name)
        return false;
    }
    public boolean existsByNameAndIdNot(String name, UUID id) {
        // repository.existsByNameAndIdNot(name, id)
        return false;
    }
}
