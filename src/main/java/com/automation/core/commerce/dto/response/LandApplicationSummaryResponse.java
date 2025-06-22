package com.automation.core.commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandApplicationSummaryResponse {
    private int totalApproved;
    private int totalRejected;
    private int totalPending;
    private int totalAppliedRequest;
    private int totalReviewed;
}
