package dev.forgepack.library.api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * Website	www.forgepack.dev
 **/

public interface ControllerInterface<DTORequest, DTOResponse> {
    ResponseEntity<DTOResponse> create(@Valid @RequestBody DTORequest created);
    ResponseEntity<Page<DTOResponse>> retrieve(String value, Pageable pageable);
    ResponseEntity<DTOResponse> retrieve(UUID id);
    ResponseEntity<DTOResponse> update(UUID id, @Valid DTORequest updated);
    ResponseEntity<DTOResponse> delete(UUID id);
}
