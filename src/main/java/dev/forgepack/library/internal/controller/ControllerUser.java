package dev.forgepack.library.internal.controller;

import dev.forgepack.library.api.controller.ControllerLifecycle;
import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.internal.payload.DTORequestUser;
import dev.forgepack.library.internal.payload.DTOResponseUser;
import dev.forgepack.library.internal.service.ServiceUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class ControllerUser extends ControllerGenericImpl<User, DTORequestUser, DTOResponseUser> implements ControllerLifecycle<DTOResponseUser> {

    private final ServiceUser serviceUser;

    public ControllerUser(ServiceUser serviceUser) {
        super(User.class, serviceUser);
        this.serviceUser = serviceUser;
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> hardDelete(@PathVariable UUID id){
        serviceUser.hardDelete(id);
        return ResponseEntity.noContent().build();
    }
//    @PreAuthorize("hasAnyRole('ADMIN') and hasAnyAuthority('user:delete')")
    @PostMapping("/{id}/restore")
    public ResponseEntity<DTOResponseUser> restore(@PathVariable UUID id){
        return ResponseEntity.accepted().body(serviceUser.restore(id));
    }
}
