package com.automation.core.lands.dto.request;

import com.automation.util.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatutoryAllocationRequest {
    private String applicantName;
    private String passportPhotos;
    private String taxClearances;
    private String declarationOfAge;
    private String administrativeCharges;
    private String processingFees;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();

}
