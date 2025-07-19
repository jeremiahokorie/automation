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
public class UserAdminRequest {
    private String email;
    private String password;
    private Long roleId;
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
