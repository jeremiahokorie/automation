package com.automation.core.lands.service.serviceImpl;


import com.automation.core.lands.service.service.CertificateOfOccupancyService;
import com.automation.core.lands.service.service.GroundRentService;
import com.automation.core.lands.service.service.ReportService;
import com.automation.core.lands.service.service.StatutoryAllocationService;
import com.automation.util.enums.LandApplicationType;
import com.automation.util.enums.ReportType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportDispatcher {
    private final Map<LandApplicationType, ReportService> serviceMap = new HashMap<>();

    public ReportDispatcher(CertificateOfOccupancyService cofoService,
                            GroundRentService groundRentService,
                            StatutoryAllocationService statutoryService) {
        serviceMap.put(LandApplicationType.COFO, (ReportService) cofoService);
        serviceMap.put(LandApplicationType.GROUND_RENT, (ReportService) groundRentService);
        serviceMap.put(LandApplicationType.STATUTORY_ALLOCATION, (ReportService) statutoryService);
    }

    public byte[] dispatch(int applicationTypeCode, LocalDate startDate, LocalDate endDate, ReportType reportType) {
        LandApplicationType appType = LandApplicationType.fromCode(applicationTypeCode);
        ReportService reportService = serviceMap.get(appType);

        if (reportService == null) {
            throw new IllegalArgumentException("No service found for application type: " + applicationTypeCode);
        }

        return reportService.generateReport(startDate, endDate, reportType);
    }

}
