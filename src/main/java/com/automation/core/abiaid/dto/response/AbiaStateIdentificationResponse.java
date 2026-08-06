package com.automation.core.abiaid.dto.response;

import com.automation.util.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AbiaStateIdentificationResponse {
    private Long id;
    private String abiaIdNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String stateOfOrigin;
    private String lga;
    private String ward;
    private String nin;
    private LocalDate dateOfBirth;
    private Status status;
    private LocalDate dateApplied;
}
