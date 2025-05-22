package com.automation.core.global.service.UserService;

import com.automation.core.global.dto.request.UserRequest;
import com.automation.core.global.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    List<UserResponse> getUsers();

    UserResponse deleteById(Long id);

    UserResponse deleteUsers(UserRequest userRequest);
}
