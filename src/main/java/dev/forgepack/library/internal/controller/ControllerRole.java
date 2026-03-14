package dev.forgepack.library.internal.controller;

import dev.forgepack.library.internal.model.Role;
import dev.forgepack.library.internal.payload.DTORequestRole;
import dev.forgepack.library.internal.payload.DTOResponseRole;
import dev.forgepack.library.internal.service.ServiceRole;
import org.springframework.web.bind.annotation.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * Website	www.forgepack.dev
 **/

@RestController
@RequestMapping("/role")
public class ControllerRole extends ControllerGeneric<Role, DTORequestRole, DTOResponseRole> {

    public ControllerRole(ServiceRole serviceRole) {
        super(serviceRole);
    }
    protected Class<Role> getEntityClass() {
        return Role.class;
    }
}
