package dev.forgepack.library.internal.controller;


import dev.forgepack.library.internal.model.Privilege;
import dev.forgepack.library.internal.payload.DTORequestPrivilege;
import dev.forgepack.library.internal.payload.DTOResponsePrivilege;
import dev.forgepack.library.internal.service.ServicePrivilege;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author	Marcelo Ribeiro Gadelha
 * Website	www.forgepack.dev
 **/

@RestController
@RequestMapping("/privilege")
public class ControllerPrivilege extends ControllerGeneric<Privilege, DTORequestPrivilege, DTOResponsePrivilege> {

    public ControllerPrivilege(ServicePrivilege servicePrivilege) {
        super(servicePrivilege);
    }
    protected Class<Privilege> getEntityClass() {
        return Privilege.class;
    }
}
