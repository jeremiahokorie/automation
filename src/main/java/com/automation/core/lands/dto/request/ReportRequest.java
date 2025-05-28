package com.automation.core.lands.dto.request;


import com.automation.util.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    private int applicationType; // 1, 2, 3
    private ReportType reportType; // DAILY, MONTHLY, YEARLY
    private LocalDate startDate;
    private LocalDate endDate;
//    private List<String> selectedMonths;
//    private List<Integer> selectedYears;

}
