package com.automation.core.inspection.service.InspectionService;

import com.automation.core.inspection.dto.request.InspectionRequest;
import com.automation.core.inspection.dto.request.StatusUpdateDto;
import com.automation.core.inspection.dto.response.InspectionResponse;

import java.util.List;

public interface InspectionService {
    InspectionResponse updateInspectionStatus(Long id, StatusUpdateDto dto);

    List<InspectionResponse> getAllInspectionRequest();

    InspectionResponse createInspection(InspectionRequest inspectionRequest);
}
