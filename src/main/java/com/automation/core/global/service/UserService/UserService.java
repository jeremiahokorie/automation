package com.automation.core.global.service.UserService;

import com.automation.core.global.dto.request.UserRequest;
import com.automation.core.global.dto.response.UserResponse;
import com.automation.core.global.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    List<UserResponse> getUsers();

    UserResponse deleteById(Long id);

    User loadUserByUsername(String email) throws UsernameNotFoundException;;

    // UserResponse deleteUsers(UserRequest userRequest);
}
