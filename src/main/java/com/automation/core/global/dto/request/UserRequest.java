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
public class UserRequest {
    //private List<Long> userIds;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
//  private String role;
    private String address;
//  private Long roleId;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String nin;
    private Long roleId;
}
