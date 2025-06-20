package com.automation.core.basepa.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentSummaryResponse {
    private int totalApproved;
    private int totalRejected;
    private int totalPending;
    private int totalRegisteredEnvironment;
    private int totalReviewed;
}
