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
public class RolesRequest {
    private String name;
    private String value;
    private String description;
    private List<Long> permissionIds;
}
