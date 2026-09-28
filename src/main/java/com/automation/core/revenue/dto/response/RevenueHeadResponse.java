package com.automation.core.revenue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueHeadResponse {
    private Long id;
    private String name;
    private String revenueHeadCode;
    private BigDecimal amount;
}
