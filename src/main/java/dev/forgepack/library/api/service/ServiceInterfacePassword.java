package dev.forgepack.library.api.service;

import dev.forgepack.library.internal.payload.DTORequestUserAuth;
import dev.forgepack.library.internal.payload.DTOResponseUser;

public interface ServiceInterfacePassword {

    DTOResponseUser changePassword(DTORequestUserAuth dtoRequestUserUser);
    DTOResponseUser resetPassword(String username);
//    void resetSecret(String username/*, String captchaToken*/);
//    void captchaTest(String captchaToken);
}
