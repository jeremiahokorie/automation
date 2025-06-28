package com.automation.core.global.service.ServiceImpl;

import com.automation.core.global.dto.request.ChangePasswordRequest;
import com.automation.core.global.dto.request.UserAdminRequest;
import com.automation.core.global.dto.request.UserRequest;
import com.automation.core.global.dto.response.AdminUserResponse;
import com.automation.core.global.dto.response.UserResponse;
import com.automation.core.global.exception.CustomException;
import com.automation.core.global.exception.Exception;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.core.global.model.Permission;
import com.automation.core.global.model.Roles;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.PermissionRepository;
import com.automation.core.global.repository.RoleRepository;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.global.service.UserService.UserService;
import com.automation.events.EmailNotificationEvent;
import com.automation.util.jwt.JwtUtil;
import com.google.common.collect.ImmutableMap;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher publisher;
    private final PermissionRepository permissionRepository;
    private final JwtUtil jwtUtil;
    private User user;
    private UserService userService;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        Optional<User> user = userRepository.findByEmail(userRequest.getEmail());

        Roles userRole = roleRepository.findByValue("SUPERADMIN")
                .orElseThrow(() -> new Exception("Default role not found"));

//        Roles role;
//        if (isAdminCreation) {
//            // For admin-created users, use the role from request
//            role = roleRepository.findById(userRequest.getRoleId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
//        } else {
//            // For self-registration, use default USER role
//            role = roleRepository.findByValue("SUPERADMIN")
//                    .orElseThrow(() -> new Exception("Default role not found"));
//        }

//        Roles role = roleRepository.findById(userRequest.getRoleId())
//                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));


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
       // publisher.publishEvent(new EmailNotificationEvent(this, "welcome", ImmutableMap.of("recipient", createUser.getEmail(), "name", createUser.getFirstName() + "  " + createUser.getLastName())));

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

//    @Override
//    public UserResponse createUser(UserRequest userRequest, boolean isAdminCreation) {
//        Optional<User> user = userRepository.findByEmail(userRequest.getEmail());
//
////        Roles userRole = roleRepository.findByValue("SUPERADMIN")
////                .orElseThrow(() -> new Exception("Default role not found"));
//
//        Roles role;
//        if (isAdminCreation) {
//            // For admin-created users, use the role from request
//            role = roleRepository.findById(userRequest.getRoleId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
//        } else {
//            // For self-registration, use default USER role
//            role = roleRepository.findByValue("SUPERADMIN")
//                    .orElseThrow(() -> new Exception("Default role not found"));
//        }
//
////        Roles role = roleRepository.findById(userRequest.getRoleId())
////                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
//
//
//        if (user.isPresent()) {
//            throw new Exception("User already exists");
//        }
//
//        User createUser = new User();
//        createUser.setEmail(userRequest.getEmail());
//        createUser.setFirstName(userRequest.getFirstName());
//        createUser.setLastName(userRequest.getLastName());
//        createUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//        createUser.setPhoneNumber(userRequest.getPhoneNumber());
//        createUser.setAddress(userRequest.getAddress());
//        createUser.setRoles(List.of(role));
//        createUser.setNin(userRequest.getNin());
//        createUser.setCity(userRequest.getCity());
//        createUser.setState(userRequest.getState());
//        createUser.setZip(userRequest.getZip());
//        createUser.setStreet(userRequest.getStreet());
//        //createUser.setRole(roles);
//        createUser.setCreateDate(LocalDate.now());
//        userRepository.save(createUser);
//        publisher.publishEvent(new EmailNotificationEvent(this, "welcome", ImmutableMap.of("recipient", createUser.getEmail(), "name", createUser.getFirstName() + "  " + createUser.getLastName())));
//
//        return buildUserResponseWithPermissions(createUser, role);
//
////                UserResponse.builder()
////                .id(createUser.getId())
////                .email(userRequest.getEmail())
////                .firstName(userRequest.getFirstName())
////                .lastName(userRequest.getLastName())
////                .phoneNumber(userRequest.getPhoneNumber())
////                .address(userRequest.getAddress())
////                .nin(userRequest.getNin())
////                .city(userRequest.getCity())
////                .state(userRequest.getState())
////                .street(userRequest.getStreet())
////                .zip(userRequest.getZip())
////                .build();
//
//    }


    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAllByOrderByCreatedAtDesc();
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

    @Override
    public UserResponse updateUser(Long userId, UserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setNin(request.getNin());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setZip(request.getZip());
        user.setStreet(request.getStreet());
        user.setZip(request.getZip());

        User updatedUser = userRepository.save(user);
        return UserResponse.builder()
                .id(updatedUser.getId())
                .email(updatedUser.getEmail())
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .phoneNumber(updatedUser.getPhoneNumber())
                .address(updatedUser.getAddress())
                .nin(updatedUser.getNin())
                .city(updatedUser.getCity())
                .state(updatedUser.getState())
                .street(updatedUser.getStreet())
                .zip(updatedUser.getZip())
                .build();
    }


    @Transactional
    public void changePassword(String email, ChangePasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new Exception("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    @Override
    public void generatePasswordResetToken(String email) {
        User user = userRepository.findByemail(email);
        if (user != null) {
            String resetToken = jwtUtil.generatePasswordResetToken(user);
            user.setResetToken(resetToken);
            user.setResetTokenExpiryDateTime(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour
            userRepository.save(user);
            String resetLink = "https://bauchi-mda.netlify.app/reset-password?token=" + resetToken;
            log.info("Reset link {}", resetLink);
            // publishResetLinkEvent(user.getEmail(), resetLink);

            publisher.publishEvent(new EmailNotificationEvent(this, "password", ImmutableMap.of("recipient", user.getEmail(), "name", user.getFirstName(), "url", resetLink)));

        }else {
            throw new Exception("User not found");
        }

    }

    @Override
    public AdminUserResponse createAdminUser(UserAdminRequest request) {
        Roles role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setFirstName(request.getName());
        user.setCreatedAt(LocalDateTime.now());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        // Automatically pull permissions from the role
        user.setPermissions(role.getPermissions());

        User saved = userRepository.save(user);

        return new AdminUserResponse(
                saved.getId(),
                saved.getFirstName(),
                saved.getEmail(),
                saved.getRole().getName(),
                saved.getPermissions().stream()
                        .map(Permission::getName)
                        .collect(Collectors.toList())
        );
    }



    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    private UserResponse buildUserResponseWithPermissions(User user, Roles role) {
        // Extract permission names from the role
        List<String> permissions = role.getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toList());

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .nin(user.getNin())
                .city(user.getCity())
                .state(user.getState())
                .street(user.getStreet())
                .zip(user.getZip())
                .build();
    }

}
