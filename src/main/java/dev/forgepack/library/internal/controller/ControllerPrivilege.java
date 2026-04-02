package dev.forgepack.library.internal.controller;

import dev.forgepack.library.api.controller.ControllerLifecycle;
import dev.forgepack.library.internal.model.Privilege;
import dev.forgepack.library.internal.payload.DTORequestPrivilege;
import dev.forgepack.library.internal.payload.DTOResponsePrivilege;
import dev.forgepack.library.internal.service.ServicePrivilege;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.UUID;

@RestController
@RequestMapping("/privilege")
public class ControllerPrivilege extends ControllerGenericImpl<Privilege, DTORequestPrivilege, DTOResponsePrivilege> implements ControllerLifecycle<DTOResponsePrivilege> {

    private final ServicePrivilege servicePrivilege;

    public ControllerPrivilege(ServicePrivilege servicePrivilege) {
        super(Privilege.class, servicePrivilege);
        this.servicePrivilege = servicePrivilege;
    }

//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> hardDelete(@PathVariable UUID id){
        servicePrivilege.hardDelete(id);
        return ResponseEntity.noContent().build();
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @PostMapping("/{id}/restore")
    public ResponseEntity<DTOResponsePrivilege> restore(@PathVariable UUID id){
        return ResponseEntity.accepted().body(servicePrivilege.restore(id));
    }
}
