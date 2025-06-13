package com.automation.core.lands.service.service;

import com.automation.core.lands.dto.request.CustomaryAllocationRequest;
import com.automation.core.lands.dto.request.LandApplicationRequest;
import com.automation.core.lands.dto.request.StatutoryApplicationRequest;
import com.automation.core.lands.dto.response.CustomaryAllocationResponse;
import com.automation.core.lands.dto.response.LandApplicationResponse;
import com.automation.core.lands.dto.response.StatutoryApplicationResponse;
import com.automation.util.enums.LandApplicationType;
import com.automation.util.enums.ReportType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LandApplicationService {

//    LandApplicationResponse applyForLand(LandApplicationRequest landApplicationRequest);
//
//    List<LandApplicationResponse> getAllApplication();

    List<LandApplicationResponse> getByTypeAndDate(LandApplicationType applicationType, LocalDate startDate, LocalDate endDate);

    List<LandApplicationResponse> getFilteredReport(LandApplicationType applicationType, String applicantName, ReportType reportType, LocalDate startDate, LocalDate endDate);

    Map<String, String> customLandApplication(CustomaryAllocationRequest customaryAllocationRequest, Map<String, MultipartFile> documents) throws IOException;

    Map<String, String> statutoryallocation(StatutoryApplicationRequest statutoryApplicationRequest, Map<String, MultipartFile> documents) throws IOException;

    List<StatutoryApplicationResponse> getAllStatutoryAllocations();

    List<CustomaryAllocationResponse> getAllCustomaryAllocations();
}
