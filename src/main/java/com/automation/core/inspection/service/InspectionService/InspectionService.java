package com.automation.core.inspection.service.InspectionService;

import com.automation.core.inspection.dto.request.InspectionRequest;
import com.automation.core.inspection.dto.request.StatusUpdateDto;
import com.automation.core.inspection.dto.response.InspectionResponse;
import com.automation.core.education.model.SchoolRegistration;
import com.automation.core.education.model.LessonCentre;
import com.automation.core.health.model.HealthFacility;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InspectionService {
    InspectionResponse updateInspectionStatus(Long id, StatusUpdateDto dto);

    List<InspectionResponse> getAllInspectionRequest();

    InspectionResponse createInspection(InspectionRequest inspectionRequest);

    // Overloaded methods to link entities directly
    InspectionResponse createInspection(InspectionRequest request, SchoolRegistration school);
    InspectionResponse createInspection(InspectionRequest request, LessonCentre centre);
    InspectionResponse createInspection(InspectionRequest request, HealthFacility facility);

    Page<InspectionResponse> getPaginatedInspection(int page, int size, String sortBy, String sortDir);

    Page<InspectionResponse> getPaginatedInspections(int offset, int pageSize);
}
