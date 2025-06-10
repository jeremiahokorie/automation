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
        createUser.setNin(userRequest.getNin());
        createUser.setCity(userRequest.getCity());
        createUser.setState(userRequest.getState());
        createUser.setZip(userRequest.getZip());
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
                .nin(user.getNin())
                .city(user.getCity())
                .state(user.getState())
                .zip(user.getZip())
                .build()).collect(Collectors.toList());
    }

//    @Override
//    public UserResponse deleteUsers(UserRequest userRequest) {
//        List<Long> idsToDelete = userRequest.getUserIds();
//        List<User> users = userRepository.findAllById(idsToDelete);
//        userRepository.deleteAll(users);
//        return new UserResponse("Deleted users: " + idsToDelete.size());
//    }

    @Override
    public UserResponse deleteById(Long id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User with Id not found"));
//        userRepository.delete(user);
        roleRepository.deleteById(id);
        userRepository.deleteById(id);
        return new UserResponse("Deleted user with ID: " + id);
    }

//    @Override
//    public User loadUserByUsername(String email) {
//        return null;
//    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        user = userRepository.findByemail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        log.info("User found with email: {}", user.getPassword());
       return user;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
    }
    
//     return new org.springframework.security.core.userdetails.User(
//             user.getEmail(),
//             user.getPassword(),
//    getAuthorities(user)
//        );

}
