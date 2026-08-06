package com.automation.core.abiaid.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AbiaStateIdentificationRequest {
    @NotBlank
    private String address;

    @NotBlank
    private String lgaId;

    @NotBlank
    private String wardId;

    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank
    private String nin;
}
