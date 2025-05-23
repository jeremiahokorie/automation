package com.automation.core.lands.service.service;

import com.automation.core.lands.dto.request.LandApplicationRequest;
import com.automation.core.lands.dto.response.LandApplicationResponse;
import com.automation.util.enums.LandApplicationType;
import com.automation.util.enums.ReportType;

import java.time.LocalDate;
import java.util.List;

public interface LandApplicationService {

    LandApplicationResponse applyForLand(LandApplicationRequest landApplicationRequest);

    List<LandApplicationResponse> getAllApplication();

    List<LandApplicationResponse> getByTypeAndDate(LandApplicationType applicationType, LocalDate startDate, LocalDate endDate);

    List<LandApplicationResponse> getFilteredReport(LandApplicationType applicationType, String applicantName, ReportType reportType, LocalDate startDate, LocalDate endDate);
}
