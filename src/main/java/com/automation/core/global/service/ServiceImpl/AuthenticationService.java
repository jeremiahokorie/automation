package com.automation.core.global.service.ServiceImpl;

import com.automation.core.abiaid.service.AbiaStateIdentificationService.AbiaStateIdentificationService;
import com.automation.core.global.exception.GlobalException;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AbiaStateIdentificationService abiaStateIdentificationService;

    public User authenticate(String email, String password) throws BadCredentialsException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return user;
    }

    public User authenticateByAbsin(String absin) {
        // 1. Verify ABSIN using the AbiaStateIdentificationService
        // This will throw ResourceNotFoundException if not found or not approved
        abiaStateIdentificationService.verifyByAbiaIdNumber(absin);

        // 2. Find the user linked to this ABSIN
        User user = userRepository.findByAbsin(absin)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not linked to this ABSIN"));

        return user;
    }
}
