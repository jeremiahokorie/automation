package com.automation.core.global.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminRequest {private String name;
    private String email;
    private String password;
    private Long roleId;
}
