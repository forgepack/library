package dev.forgepack.library.internal.controller;

import dev.forgepack.library.internal.payload.DTORequestToken;
import dev.forgepack.library.internal.payload.DTOResponseToken;
import dev.forgepack.library.internal.payload.DTOResponseUser;
import dev.forgepack.library.internal.payload.DTORequestUserAuth;
import dev.forgepack.library.internal.service.ServiceAuthenticationImpl;
import dev.forgepack.library.internal.service.ServiceUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class ControllerAuthentication {

    private final ServiceAuthenticationImpl serviceAuthenticationImpl;
    private final ServiceUser serviceUser;

    public ControllerAuthentication(ServiceAuthenticationImpl serviceAuthenticationImpl, ServiceUser serviceUser) {
        this.serviceAuthenticationImpl = serviceAuthenticationImpl;
        this.serviceUser = serviceUser;
    }

    @PostMapping("/login")
    public ResponseEntity<DTOResponseToken> login(@RequestBody @Valid DTORequestUserAuth value){
        return ResponseEntity.ok().body(serviceAuthenticationImpl.login(value));
    }
    @PostMapping("/refresh")
    public ResponseEntity<DTOResponseToken> refresh(@RequestBody @Valid DTORequestToken value){
        return ResponseEntity.accepted().body(serviceAuthenticationImpl.refresh(value));
    }
    @DeleteMapping("/logout/{refreshToken}")
    public ResponseEntity<DTOResponseToken> logout(@PathVariable("refreshToken") UUID refreshToken) {
        return ResponseEntity.accepted().body(serviceAuthenticationImpl.logout(refreshToken));
    }
    @PutMapping("/changePassword")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public ResponseEntity<DTOResponseUser> changePassword(@RequestBody @Valid DTORequestUserAuth updated){
        return ResponseEntity.accepted().body(serviceUser.changePassword(updated));
    }
    @PutMapping("/resetPassword")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER', 'VIEWER')")
    public ResponseEntity<DTOResponseUser> resetPassword(@RequestBody DTORequestUserAuth updated) {
        return ResponseEntity.accepted().body(serviceUser.resetPassword(updated.username()));
    }
    @PutMapping("/resetSecret")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER', 'VIEWER')")
    public ResponseEntity<DTOResponseUser> resetSecret(@RequestBody DTORequestUserAuth updated) {
        return ResponseEntity.accepted().body(serviceUser.resetSecret(updated.username()));
    }
}
