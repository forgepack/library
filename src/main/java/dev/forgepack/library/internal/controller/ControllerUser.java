package dev.forgepack.library.internal.controller;

import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.internal.payload.DTORequestUser;
import dev.forgepack.library.internal.payload.DTOResponseUser;
import dev.forgepack.library.internal.service.ServiceUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author	Marcelo Ribeiro Gadelha
 * Website	www.forgepack.dev
 **/

@RestController
@RequestMapping("/user")
public class ControllerUser extends ControllerGeneric<User, DTORequestUser, DTOResponseUser> {

    public ControllerUser(ServiceUser serviceUser) {
        super(serviceUser);
    }
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
