package com.automation.core.global.service.ServiceImpl;

import com.automation.core.global.dto.request.UserRequest;
import com.automation.core.global.dto.response.UserResponse;
import com.automation.core.global.exception.CustomException;
import com.automation.core.global.exception.Exception;
import com.automation.core.global.model.Roles;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.RoleRepository;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.global.service.UserService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private User user;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        Optional<User> user = userRepository.findByEmail(userRequest.getEmail());
        Roles userRole = roleRepository.findByValue("SUPERADMIN")
                .orElseThrow(() -> new Exception("Default role not found"));

        if (user.isPresent()) {
            throw new Exception("User already exists");
        }

        User createUser = new User();
        createUser.setEmail(userRequest.getEmail());
        createUser.setFirstName(userRequest.getFirstName());
        createUser.setLastName(userRequest.getLastName());
        createUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        createUser.setPhoneNumber(userRequest.getPhoneNumber());
        createUser.setAddress(userRequest.getAddress());
        createUser.setRoles(List.of(userRole));
        createUser.setNin(userRequest.getNin());
        createUser.setCity(userRequest.getCity());
        createUser.setState(userRequest.getState());
        createUser.setZip(userRequest.getZip());
        createUser.setStreet(userRequest.getStreet());
        //createUser.setRole(roles);
        createUser.setCreateDate(LocalDate.now());
        userRepository.save(createUser);

        return UserResponse.builder()
                .id(createUser.getId())
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .phoneNumber(userRequest.getPhoneNumber())
                .address(userRequest.getAddress())
                .nin(userRequest.getNin())
                .city(userRequest.getCity())
                .state(userRequest.getState())
                .street(userRequest.getStreet())
                .zip(userRequest.getZip())
                .build();
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .address(user.getAddress())
                .street(user.getStreet())
                .nin(user.getNin())
                .city(user.getCity())
                .state(user.getState())
                .zip(user.getZip())
                .build()).collect(Collectors.toList());
    }

    @Override
    public UserResponse deleteById(Long id) {
//      User user = userRepository.findById(id).orElseThrow(() -> new Exception("User with Id not found"));
//      userRepository.delete(user);
        roleRepository.deleteById(id);
        userRepository.deleteById(id);
        return new UserResponse("Deleted user with ID: " + id);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//        log.info("User found with email: {}", user.getPassword());
       return user;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
