package dev.forgepack.library.api.service;

import dev.forgepack.library.internal.payload.DTORequestToken;
import dev.forgepack.library.internal.payload.DTORequestUserAuth;
import dev.forgepack.library.internal.payload.DTOResponseToken;
import java.util.UUID;

public interface ServiceInterfaceAuth<DTORequest, DTOResponse> {

    DTOResponseToken login(DTORequestUserAuth dtoRequestUserAuth);
    DTOResponseToken refresh(DTORequestToken dtoRequestToken);
    DTOResponseToken logout(UUID refreshToken);
    void addAttempt(DTORequestUserAuth dtoRequestUserAuth);
    void resetPassword(String username/*, String captchaToken*/);
    void resetTotp(String username/*, String captchaToken*/);
    void captchaTest(String captchaToken);
}
