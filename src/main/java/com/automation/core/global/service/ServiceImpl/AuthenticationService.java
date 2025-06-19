package com.automation.core.global.service.ServiceImpl;


import com.automation.core.global.exception.Exception;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User authenticate(String email, String password) throws BadCredentialsException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return user;
    }
}

    //authenticate username password method and return the user
//    public User authenticate(String email, String password) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new Exception("User with email " + email + " not found"));
//      String hashedPassword = DigestUtils.sha256Hex(user.getPassword());
//      String incomingHashedPassword = DigestUtils.sha256Hex(password);
//      if (!hashedPassword.equals(incomingHashedPassword)) {
//          throw new Exception("Password doesn't match");
//      }
//      return user;
//    }


