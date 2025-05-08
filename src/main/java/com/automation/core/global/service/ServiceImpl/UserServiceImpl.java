package com.automation.core.global.service.ServiceImpl;

import com.automation.core.global.dto.request.UserRequest;
import com.automation.core.global.dto.response.UserResponse;
import com.automation.core.global.exception.CustomException;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.global.service.UserService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        Optional<User> user = userRepository.findByemail(userRequest.getEmail());
        if (user.isPresent()) {
            throw new CustomException("User already exists");
        }

        User createUser = new User();
        createUser.setEmail(userRequest.getEmail());
        createUser.setFirstName(userRequest.getFirstName());
        createUser.setLastName(userRequest.getLastName());
        createUser.setPassword(userRequest.getPassword());
        createUser.setPhoneNumber(userRequest.getPhoneNumber());
        createUser.setAddress(userRequest.getAddress());
        userRepository.save(createUser);

        return UserResponse.builder()
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .phoneNumber(userRequest.getPhoneNumber())
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
                .build()).collect(Collectors.toList());
    }
}
