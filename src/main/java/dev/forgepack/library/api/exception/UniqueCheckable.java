package dev.forgepack.library.api.exception;

import java.util.UUID;

/**
 * Interface to Unique Checkable
 *
 * @author	Marcelo Ribeiro Gadelha
 * Email:	gadelha.ti@gmail.com
 * Website:	www.forgepack.dev
 **/

public interface UniqueCheckable {

    boolean existsByField(String field, Object value);
    boolean existsByFieldAndIdNot(String field, Object value, UUID id);
}
