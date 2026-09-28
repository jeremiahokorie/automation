package com.automation.core.revenue.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueHeadRequest {
    private String name;
    private String revenueHeadCode;
    private BigDecimal amount;
}
