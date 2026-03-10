package dev.forgepack.library.api.exception;

import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.forgepack.dev
 **/

public interface UniqueCheckable {

    boolean existsByField(String field, Object value);
    boolean existsByFieldAndIdNot(String field, Object value, UUID id);
}
