//package dev.forgepack.library.internal.controller;
//
//import com.maps.exception.ApiError;
//import com.maps.persistence.model.User;
//import com.maps.persistence.payload.request.DTORequestUser;
//import com.maps.persistence.payload.request.DTORequestUserAuth;
//import com.maps.persistence.payload.request.DTORequestUserPassword;
//import com.maps.persistence.payload.response.DTOResponseUser;
//import com.maps.service.ServiceUser;
//import com.maps.service.ServiceAuth;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author	Marcelo Ribeiro Gadelha
// * Website	www.forgepack.dev
// **/
//
//@RestController
//@RequestMapping("/user")
//public class ControllerUser extends ControllerGeneric<User, DTORequestUser, DTOResponseUser> {
//
//    private final ServiceUser serviceUser;
//    private final ServiceAuth serviceAuth;
//
//    public ControllerUser(ServiceUser serviceUser, ServiceAuth serviceAuth) {
//        super(serviceUser);
//        this.serviceUser = serviceUser;
//        this.serviceAuth = serviceAuth;
//    }
//    protected Class<User> getEntityClass() {
//        return User.class;
//    }
//    @PostMapping("/signup")
//    public ResponseEntity<ApiError> signUp(@RequestBody @Valid DTORequestUser value) {
//        serviceAuth.register(value.username(), value.email());
//        return ResponseEntity.accepted().body(new ApiError(HttpStatus.CREATED, "", ""));
//    }
//    @PutMapping("/changePassword")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
//    public ResponseEntity<DTOResponseUser> changePassword(@RequestBody @Valid DTORequestUserPassword updated){
//        return ResponseEntity.accepted().body(serviceUser.changePassword(updated));
//    }
//    @PutMapping("/resetPassword")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER', 'VIEWER')")
//    public ResponseEntity<DTOResponseUser> resetPassword(@RequestBody DTORequestUserAuth updated) {
//        return ResponseEntity.accepted().body(serviceUser.resetPassword(updated.username()));
//    }
//    @PutMapping("/resetTotp")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER', 'VIEWER')")
//    public ResponseEntity<DTOResponseUser> resetSecret(@RequestBody DTORequestUserAuth updated) {
//        return ResponseEntity.accepted().body(serviceUser.resetSecret(updated.username()));
//    }
//}
