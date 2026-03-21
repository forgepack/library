package dev.forgepack.library.internal.controller;

import dev.forgepack.library.internal.exception.ApiError;
import dev.forgepack.library.internal.payload.*;
import dev.forgepack.library.internal.service.ServiceAuth;
import dev.forgepack.library.internal.service.ServiceUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class ControllerAuth {

    private final ServiceAuth serviceAuth;
    private final ServiceUser serviceUser;

    public ControllerAuth(ServiceAuth serviceAuth, ServiceUser serviceUser) {
        this.serviceAuth = serviceAuth;
        this.serviceUser = serviceUser;
    }

    @PostMapping("/login")
    public ResponseEntity<DTOResponseToken> login(@RequestBody @Valid DTORequestUserAuth value){
        return ResponseEntity.ok().body(serviceAuth.login(value));
    }
    @PostMapping("/signup")
    public ResponseEntity<ApiError> signUp(@RequestBody DTORequestUser value) {
        System.out.println("Username:" + value.username());
        System.out.println("Email:" + value.email());
        serviceAuth.register(value.username(), value.email());
        return ResponseEntity.accepted().body(new ApiError(HttpStatus.CREATED, "", ""));
    }
//    @PutMapping("/changePassword")
////    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
//    public ResponseEntity<DTOResponseUser> changePassword(@RequestBody @Valid DTORequestUserPassword updated){
//        return ResponseEntity.accepted().body(serviceUser.changePassword(updated));
//    }
//    @PutMapping("/resetPassword")
////    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER', 'VIEWER')")
//    public ResponseEntity<DTOResponseUser> resetPassword(@RequestBody DTORequestUserAuth updated) {
//        return ResponseEntity.accepted().body(serviceUser.resetPassword(updated.username()));
//    }
//    @PutMapping("/resetTotp")
////    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER', 'VIEWER')")
//    public ResponseEntity<DTOResponseUser> resetSecret(@RequestBody DTORequestUserAuth updated) {
//        return ResponseEntity.accepted().body(serviceUser.resetSecret(updated.username()));
//    }
    @PostMapping("/refresh")
    public ResponseEntity<DTOResponseToken> refresh(@RequestBody @Valid DTORequestToken value){
        return ResponseEntity.accepted().body(serviceAuth.refresh(value));
    }
    @DeleteMapping("/logout/{refreshToken}")
    public ResponseEntity<DTOResponseToken> logout(@PathVariable("refreshToken") UUID refreshToken) {
        return ResponseEntity.accepted().body(serviceAuth.logout(refreshToken));
    }
}
