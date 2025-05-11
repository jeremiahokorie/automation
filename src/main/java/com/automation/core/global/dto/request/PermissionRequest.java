package com.automation.core.global.dto.request;

import lombok.Data;

@Data
public class PermissionRequest {
    private Long id;
    private String name;
    private String description;
    private String code;

}
