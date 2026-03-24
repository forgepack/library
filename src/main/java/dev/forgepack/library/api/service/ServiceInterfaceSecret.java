package dev.forgepack.library.api.service;

import dev.forgepack.library.internal.payload.DTOResponseUser;

public interface ServiceInterfaceSecret {

    DTOResponseUser resetSecret(String username/*, String captchaToken*/);
//    void captchaTest(String captchaToken);
}
