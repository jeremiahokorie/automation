package com.automation.core.global.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class AdminUserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private List<String> permissions;
}
