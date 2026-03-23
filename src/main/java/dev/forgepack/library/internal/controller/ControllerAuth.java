package dev.forgepack.library.internal.controller;

import dev.forgepack.library.internal.payload.DTORequestToken;
import dev.forgepack.library.internal.payload.DTOResponseToken;
import dev.forgepack.library.internal.payload.DTOResponseUser;
import dev.forgepack.library.internal.payload.DTORequestUserAuth;
import dev.forgepack.library.internal.service.ServiceAuth;
import dev.forgepack.library.internal.service.ServicePassword;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class ControllerAuth {

    private final ServiceAuth serviceAuth;
    private final ServicePassword servicePassword;

    public ControllerAuth(ServiceAuth serviceAuth, ServicePassword servicePassword) {
        this.serviceAuth = serviceAuth;
        this.servicePassword = servicePassword;
    }

    @PostMapping("/login")
    public ResponseEntity<DTOResponseToken> login(@RequestBody @Valid DTORequestUserAuth value){
        return ResponseEntity.ok().body(serviceAuth.login(value));
    }
    @PostMapping("/refresh")
    public ResponseEntity<DTOResponseToken> refresh(@RequestBody @Valid DTORequestToken value){
        return ResponseEntity.accepted().body(serviceAuth.refresh(value));
    }
    @DeleteMapping("/logout/{refreshToken}")
    public ResponseEntity<DTOResponseToken> logout(@PathVariable("refreshToken") UUID refreshToken) {
        return ResponseEntity.accepted().body(serviceAuth.logout(refreshToken));
    }
    @PutMapping("/changePassword")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public ResponseEntity<DTOResponseUser> changePassword(@RequestBody @Valid DTORequestUserAuth updated){
        return ResponseEntity.accepted().body(servicePassword.changePassword(updated));
    }
    @PutMapping("/resetPassword")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER', 'VIEWER')")
    public ResponseEntity<DTOResponseUser> resetPassword(@RequestBody DTORequestUserAuth updated) {
        return ResponseEntity.accepted().body(servicePassword.resetPassword(updated.username()));
    }
//    @PutMapping("/resetTotp")
////    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER', 'VIEWER')")
//    public ResponseEntity<DTOResponseUser> resetSecret(@RequestBody DTORequestUserAuth updated) {
//        return ResponseEntity.accepted().body(servicePassword.resetSecret(updated.username()));
//    }
}
