package com.automation.core.global.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class AdminUserResponse {
    private Long id;
    private String email;
    private String role;
    private List<String> permissions;
//    private Long roleId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String nin;
}
