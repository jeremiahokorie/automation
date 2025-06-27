package com.automation.core.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String nin;
    private String roleName;
    private List<String> permissions;
    private Boolean isPayed;

    public UserResponse(String s) {
    }
}
