package com.automation.core.global.service.UserService;

import com.automation.core.global.dto.request.ChangePasswordRequest;
import com.automation.core.global.dto.request.UserAdminRequest;
import com.automation.core.global.dto.request.UserRequest;
import com.automation.core.global.dto.response.AdminUserResponse;
import com.automation.core.global.dto.response.UserResponse;
import com.automation.core.global.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    List<UserResponse> getUsers();

    UserResponse deleteById(Long id);

    User loadUserByUsername(String email) throws UsernameNotFoundException;;

    UserResponse updateUser(Long userId, UserRequest request);

    void changePassword(String name, ChangePasswordRequest request);

    void generatePasswordResetToken(String email);

    AdminUserResponse createAdminUser(UserAdminRequest request);

    Page<UserResponse> getAllUsers(int page, int size, String sortBy, String sortDir);

    Page<UserResponse> getPaginatedUsers(int offset, int pageSize);


    // UserResponse deleteUsers(UserRequest userRequest);
}
