package com.automation.core.lga.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalGovernmentRequest {
    @NotBlank(message = "LGA name is required")
    private String name;
    private String headquarters;
}
