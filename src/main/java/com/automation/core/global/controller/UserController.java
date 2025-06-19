package com.automation.core.global.controller;


import com.automation.core.global.dto.request.AuthRequest;
import com.automation.core.global.dto.request.UserRequest;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.global.dto.response.AuthResponse;
import com.automation.core.global.dto.response.UserResponse;
import com.automation.core.global.service.UserService.UserService;
import com.automation.util.constant.AppConstant;
import com.automation.util.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/auth")
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

   // @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<AppResponse<List<UserResponse>>> getUsers() {
        List<UserResponse> users = userService.getUsers();
        return ResponseEntity.ok().body(AppResponse.<List<UserResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(users).build());
    }


    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
    @DeleteMapping("/admin/{id}/user")
    public ResponseEntity<AppResponse<UserResponse>> deleteUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.deleteById(id);
        return ResponseEntity.ok().body(AppResponse.<UserResponse>builder()
                .message(AppConstant.ApiResponseMessage.DELETE)
                .status(HttpStatus.OK.value()).data(userResponse).build()
        );
    }


//    @PutMapping("/{userId}")
//    public ResponseEntity<UserRequest> updateUser(
//            @PathVariable Long userId,
//            @RequestBody UserRequest request) {
//        return ResponseEntity.ok(userService.updateUser(userId, request));
//    }
//
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
//        userService.deleteUser(userId);
//        return ResponseEntity.noContent().build();
//    }
}
