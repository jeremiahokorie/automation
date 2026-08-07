package com.automation.core.wardactivity.service;

import com.automation.core.wardactivity.dto.request.CreateActivityReportRequest;
import com.automation.core.wardactivity.dto.request.CreateWardRequest;
import com.automation.core.wardactivity.dto.request.UpdateActivityReportRequest;
import com.automation.core.wardactivity.dto.response.ActivityReportResponse;
import com.automation.core.wardactivity.dto.response.WardSimpleResponse;
import com.automation.core.wardactivity.enums.ActivityCategory;
import com.automation.core.wardactivity.enums.ActivityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Map;

public interface WardActivityService {
    WardSimpleResponse createWard(CreateWardRequest request, String actorEmail);
    Page<WardSimpleResponse> getWards(Pageable pageable, String actorEmail);

    ActivityReportResponse createReport(CreateActivityReportRequest request, String actorEmail);
    Page<ActivityReportResponse> getReports(
            String actorEmail,
            String wardId,
            ActivityStatus status,
            ActivityCategory category,
            LocalDate from,
            LocalDate to,
            Pageable pageable
    );
    ActivityReportResponse getReportById(Long id, String actorEmail);
    ActivityReportResponse updateReport(Long id, UpdateActivityReportRequest request, String actorEmail);

    Map<String, Object> getDashboardSummary(String actorEmail);
}
