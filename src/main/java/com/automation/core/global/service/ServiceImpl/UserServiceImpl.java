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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        Optional<User> user = userRepository.findByemail(userRequest.getEmail());
        Roles userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new Exception("Default role USER not found"));
        if (user.isPresent()) {
            throw new Exception("User already exists");
        }

//        Set<Roles> roles = new HashSet<>();
//        for(String rolename : user.getPermissions()){
//            Optional<Roles> role = roleRepository.findByName(rolename);
//            if(role.isPresent()){
//                roles.add(role.get());
//            }else {
//                throw new CustomException("Role not found" + rolename);
//            }
//
//        }

        User createUser = new User();
        createUser.setEmail(userRequest.getEmail());
        createUser.setFirstName(userRequest.getFirstName());
        createUser.setLastName(userRequest.getLastName());
        createUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        createUser.setPhoneNumber(userRequest.getPhoneNumber());
        createUser.setAddress(userRequest.getAddress());
        createUser.setRole(userRole);
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
                .build()).collect(Collectors.toList());
    }

    @Override
    public UserResponse deleteUsers(UserRequest userRequest) {
        List<Long> idsToDelete = userRequest.getUserIds();
        List<User> users = userRepository.findAllById(idsToDelete);
        userRepository.deleteAll(users);
        return new UserResponse("Deleted users: " + idsToDelete.size());
    }

    @Override
    public UserResponse deleteById(Long id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User with Id not found"));
//        userRepository.delete(user);
        roleRepository.deleteById(id);
        userRepository.deleteById(id);
        return new UserResponse("Deleted user with ID: " + id);
    }
}
