package com.automation.core.global.service.ServiceImpl;

import com.automation.core.global.dto.request.UserRequest;
import com.automation.core.global.dto.response.UserResponse;
import com.automation.core.global.exception.CustomException;
import com.automation.core.global.model.Roles;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.RoleRepository;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.global.service.UserService.UserService;
import com.automation.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        Optional<User> user = userRepository.findByemail(userRequest.getEmail());
//        Roles role = roleRepository.findById(userRequest.getRoleId())
//                .orElseThrow(() -> new CustomException("Role not found"));

        if (user.isPresent()) {
            throw new CustomException("User already exists");
        }

        User createUser = new User();
        createUser.setEmail(userRequest.getEmail());
        createUser.setFirstName(userRequest.getFirstName());
        createUser.setLastName(userRequest.getLastName());
        createUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        createUser.setPhoneNumber(userRequest.getPhoneNumber());
        createUser.setAddress(userRequest.getAddress());
        createUser.setRole("USER");
        userRepository.save(createUser);

        return UserResponse.builder()
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .phoneNumber(userRequest.getPhoneNumber())
                .address(userRequest.getAddress())
                .build();
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserResponse.builder()
                .email(user.getEmail())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .address(user.getAddress())
                .build()).collect(Collectors.toList());
    }
}
