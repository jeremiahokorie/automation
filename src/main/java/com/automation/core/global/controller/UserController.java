package com.automation.core.global.controller;


import com.automation.core.commerce.dto.response.BusinessRegistrationResponse;
import com.automation.core.global.dto.request.AuthRequest;
import com.automation.core.global.dto.request.ChangePasswordRequest;
import com.automation.core.global.dto.request.UserAdminRequest;
import com.automation.core.global.dto.request.UserRequest;
import com.automation.core.global.dto.response.AdminUserResponse;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.global.dto.response.AuthResponse;
import com.automation.core.global.dto.response.UserResponse;
import com.automation.core.global.model.User;
import com.automation.core.global.service.UserService.UserService;
import com.automation.util.constant.AppConstant;
import com.automation.util.jwt.JwtUtil;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired private AuthenticationManager authManager;

    @PostMapping("/register")
    public ResponseEntity<AppResponse<UserResponse>> createUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        AppResponse<UserResponse> response = AppResponse.<UserResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(userResponse).error("").build();
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AppResponse<AdminUserResponse>> createAdminUser(@RequestBody UserAdminRequest request) {
        AdminUserResponse adminResponse = userService.createAdminUser(request);
        AppResponse<AdminUserResponse> response = AppResponse.<AdminUserResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(adminResponse).error("").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping("/admin/users")
//   // @PreAuthorize("hasRole('SUPERADMIN')")
//    public ResponseEntity<AppResponse<UserResponse>> createUserAsAdmin(@RequestBody UserRequest userRequest) {
//        UserResponse userResponse = userService.createUser(userRequest);
//        AppResponse<UserResponse> response = AppResponse.<UserResponse>builder()
//                .message(AppConstant.ApiResponseMessage.CREATED)
//                .status(HttpStatus.OK.value()).data(userResponse).error("").build();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//jigawa state commodity xchange service

   // @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<AppResponse<List<UserResponse>>> getUsers() {
        List<UserResponse> users = userService.getUsers();
        return ResponseEntity.ok().body(AppResponse.<List<UserResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(users).build());
    }

    @GetMapping("/paginated/users")
    public ResponseEntity<AppResponse<Page<UserResponse>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Page<UserResponse> users = userService.getAllUsers(page, size, sortBy, sortDir);
        AppResponse<Page<UserResponse>> response = AppResponse.<Page<UserResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value())
                .data(users)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated/users/{offset}/{pageSize}")
    public ResponseEntity<AppResponse<Page<UserResponse>>> getPaginatedUsers(
            @PathVariable int offset,
            @PathVariable int pageSize) {
        Page<UserResponse> users = userService.getPaginatedUsers(offset - 1, pageSize);
        AppResponse<Page<UserResponse>>response = AppResponse.<Page<UserResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value())
                .data(users)
                .build();
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/paginated/users/{offset}/{pageSize}")
//    public ResponseEntity<AppResponse<Page<UserResponse>>>getPaginatedUsers(@PathVariable int offset, @PathVariable int pageSize){
//        Page<UserResponse> users = userService.getPaginatedUsers(offset, pageSize);
//        AppResponse<Page<UserResponse>> response = AppResponse.<Page<UserResponse>>builder()
//                .message(AppConstant.ApiResponseMessage.GET)
//               // .recordCount(users.getSize())
//                .status(HttpStatus.OK.value())
//                .data(users)
//                .build();
//        return ResponseEntity.ok(response);
//    }

    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
    @DeleteMapping("/admin/{id}/user")
    public ResponseEntity<AppResponse<UserResponse>> deleteUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.deleteById(id);
        return ResponseEntity.ok().body(AppResponse.<UserResponse>builder()
                .message(AppConstant.ApiResponseMessage.DELETE)
                .status(HttpStatus.OK.value()).data(userResponse).build()
        );
    }

    @PutMapping("/users/{userId}")
    // @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<AppResponse<UserResponse>> updateUser(@PathVariable Long userId,@Valid @RequestBody UserRequest request) {
        UserResponse userResponse = userService.updateUser(userId, request);
        return ResponseEntity.ok().body(AppResponse.<UserResponse>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(userResponse).build());
    }

    @PostMapping("/users/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal principal) {
        userService.changePassword(principal.getName(), request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/generate-password-reset-token")
    public ResponseEntity<String> generatePasswordResetToken(@RequestParam String email) {
        userService.generatePasswordResetToken(email);
        return ResponseEntity.ok("Password reset token generated and sent to the user's email.");
    }
}
