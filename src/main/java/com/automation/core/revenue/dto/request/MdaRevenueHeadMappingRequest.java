package com.automation.core.revenue.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MdaRevenueHeadMappingRequest {
    private Long mdaId;
    private Long revenueHeadId;
}
