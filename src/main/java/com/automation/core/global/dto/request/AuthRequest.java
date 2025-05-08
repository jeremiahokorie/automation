package com.automation.core.global.dto.request;
import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}