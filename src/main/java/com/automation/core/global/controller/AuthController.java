package com.automation.core.global.controller;

import com.automation.core.global.dto.request.AuthRequest;
import com.automation.core.global.dto.request.ChangePasswordRequest;
import com.automation.core.global.dto.request.ResetPasswordRequest;
import com.automation.core.global.dto.request.UserRequest;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.global.dto.response.AuthResponse;
import com.automation.core.global.dto.response.UserResponse;
import com.automation.core.global.model.User;
import com.automation.core.global.service.ServiceImpl.AuthenticationService;
import com.automation.core.global.service.UserService.RolesService;
import com.automation.core.global.service.UserService.UserService;
import com.automation.util.constant.AppConstant;
import com.automation.util.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RolesService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired private AuthenticationManager authManager;
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<AppResponse<AuthResponse>> authenticate(@RequestBody AuthRequest request) {
        log.info("UserDetailsccc: {}", request.getEmail());
        try{
       // authenticationService.authenticate(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//      UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            log.info("UserDetail: {}", request.getEmail());
        User userDetails = userService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok()
                .body(AppResponse.of(HttpStatus.OK.value(),new AuthResponse(token)));
    } catch (UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(AppResponse.error(HttpStatus.UNAUTHORIZED.value(), "Invalid email address"));

    } catch (BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(AppResponse.error(HttpStatus.UNAUTHORIZED.value(), "Invalid password"));

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(AppResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Authentication failed"));
    }
    }



    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (!jwtUtil.validateToken(refreshToken, true)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String newAccessToken = jwtUtil.generateAccessToken(userDetails);

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken
        ));
    }




}
