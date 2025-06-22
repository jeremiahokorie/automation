package com.automation.core.commerce.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandApplicationSummaryRequest {
    private int totalApproved;
    private int totalRejected;
    private int totalPending;
    private int totalAppliedRequest;
    private int totalReviewed;
}
