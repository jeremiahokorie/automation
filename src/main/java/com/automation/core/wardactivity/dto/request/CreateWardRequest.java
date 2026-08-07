package com.automation.core.wardactivity.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateWardRequest {

    @NotBlank(message = "Ward name is required")
    private String name;

    @NotBlank(message = "Ward code is required")
    private String code;

    @NotBlank(message = "Local government id is required")
    private String localGovernmentId;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getLocalGovernmentId() {
        return localGovernmentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLocalGovernmentId(String localGovernmentId) {
        this.localGovernmentId = localGovernmentId;
    }
}
