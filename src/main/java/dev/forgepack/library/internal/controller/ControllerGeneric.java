package dev.forgepack.library.internal.controller;

import dev.forgepack.library.api.controller.ControllerInterface;
import dev.forgepack.library.api.payload.DTORequestIdentifiable;
import dev.forgepack.library.internal.model.GenericAuditEntity;
import dev.forgepack.library.internal.service.ServiceGeneric;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * Website	www.forgepack.dev
 **/

public abstract class ControllerGeneric<T extends GenericAuditEntity, I extends DTORequestIdentifiable<UUID>, O extends RepresentationModel<O>> implements ControllerInterface<I, O> {

    private final ServiceGeneric<T, I, O> serviceInterface;

    public ControllerGeneric(ServiceGeneric<T, I, O> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    protected abstract Class<T> getEntityClass();
    @PostMapping("")
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:create')")
    public ResponseEntity<O> create(@RequestBody @Valid I created){
        O body = serviceInterface.create(created);
        Object id = null;
        try {
            id = body.getClass().getMethod("getId").invoke(body);
        } catch (Exception ignored) { }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).body(body);
    }
    @GetMapping("")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER') and hasAnyAuthority('user:retrieve')")
    public ResponseEntity<Page<O>> findAll(@RequestParam(name = "value", defaultValue = "", required = false) String value, Pageable pageable){
        return ResponseEntity.ok().body(serviceInterface.findAll(pageable, value, getEntityClass()));
    }
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER') and hasAnyAuthority('user:retrieve')")
    public ResponseEntity<O> findById(@PathVariable UUID id){
        return ResponseEntity.ok().body(serviceInterface.findById(id));
    }
    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR') and hasAnyAuthority('user:update')")
    public ResponseEntity<O> update(@PathVariable UUID id, @RequestBody @Valid I updated){
        return ResponseEntity.accepted().body(serviceInterface.update(updated));
    }
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    public ResponseEntity<O> delete(@PathVariable UUID id){
        return ResponseEntity.accepted().body(serviceInterface.softDelete(id));
    }
}
