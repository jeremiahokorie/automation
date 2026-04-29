package com.automation.core.inspection.service.InspectionServiceImpl;

import com.automation.core.basepa.model.EnvironmentApplication;
import com.automation.core.basepa.repository.EnvironmentRepository;
import com.automation.core.commerce.dto.response.BusinessRegistrationResponse;
import com.automation.core.commerce.model.BusinessRegistration;
import com.automation.core.commerce.repository.BusinessRepository;
import com.automation.core.global.exception.Exception;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.inspection.dto.request.InspectionRequest;
import com.automation.core.inspection.dto.request.StatusUpdateDto;
import com.automation.core.inspection.dto.response.InspectionResponse;
import com.automation.core.inspection.model.Inspection;
import com.automation.core.inspection.repository.InspectionRepository;
import com.automation.core.inspection.service.InspectionService.InspectionService;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InspectionServiceImpl implements InspectionService {
    private final InspectionRepository inspectionRepository;
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final EnvironmentRepository environmentRepository;


    @Override
    @Transactional
    public InspectionResponse updateInspectionStatus(Long id, StatusUpdateDto dto) {
        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new Exception("Inspection not found"));

        Status newStatus = Status.valueOf(dto.getNewStatus());
        inspection.setStatus(newStatus);
        inspection.setNotes(dto.getNotes());
        inspection.setAssignedTo(dto.getUpdatedBy());
        inspection.setUpdatedAt(LocalDateTime.now());

        if (inspection.getBusinessRegistration() != null) {
            BusinessRegistration business = inspection.getBusinessRegistration();
            business.setStatus(newStatus);
            businessRepository.save(business);
        }

        if (inspection.getEnvironment() != null) {
            EnvironmentApplication environment = inspection.getEnvironment();
            environment.setStatus(newStatus);
            environmentRepository.save(environment);
        }

        inspectionRepository.save(inspection);

        return InspectionResponse.builder().id(inspection.getId()).build();
    }

//    @Override
//    public InspectionResponse updateInspectionStatus(Long id, StatusUpdateDto dto) {
//        Inspection inspection = inspectionRepository.findById(id)
//                .orElseThrow(() -> new Exception("Inspection not found"));
//        inspection.setStatus(Status.valueOf(dto.getNewStatus()));
//        inspection.setNotes(dto.getNotes());
//        inspection.setAssignedTo(dto.getUpdatedBy());
//        inspection.setUpdatedAt(LocalDateTime.now());
//        inspectionRepository.save(inspection);
//        return InspectionResponse.builder().id(inspection.getId()).build();
//    }

    @Override
    public List<InspectionResponse> getAllInspectionRequest() {
        List<Inspection> inspections = inspectionRepository.findAllByOrderByCreatedAtDesc();
        return inspections.stream().map(inspection -> InspectionResponse.builder()
                        .id(inspection.getId())
                        .applicantName(inspection.getApplicantName())
                        .requestId(inspection.getRequestId())
                        .applicationType(inspection.getApplicationType())
                        .status(inspection.getStatus())
                        .notes(inspection.getNotes())
                        .sourceService(inspection.getSourceService())
                        .createdAt(inspection.getCreatedAt())
                        .updatedAt(inspection.getUpdatedAt())
                        .assignedTo(inspection.getAssignedTo())
                        .build())
                        .collect(Collectors.toList());
    }

    @Override
    public InspectionResponse createInspection(InspectionRequest inspectionRequest) {
        Inspection inspection = new Inspection();
        inspection.setRequestId(inspectionRequest.getRequestId());
        inspection.setSourceService(inspectionRequest.getSourceService());
        inspection.setApplicantName(inspectionRequest.getApplicantName());
        inspection.setApplicationType(inspectionRequest.getApplicationType());
        inspection.setStatus(Status.PENDING);
        inspection.setCreatedAt(LocalDateTime.now());
        inspectionRepository.save(inspection);
        return InspectionResponse.builder().id(inspection.getId()).build();
    }

    @Override
    public Page<InspectionResponse> getPaginatedInspection(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return inspectionRepository.findAll(pageRequest)
                .map(inspection -> InspectionResponse.builder()
                        .id(inspection.getId())
                        .applicantName(inspection.getApplicantName())
                        .requestId(inspection.getRequestId())
                        .applicationType(inspection.getApplicationType())
                        .status(inspection.getStatus())
                        .notes(inspection.getNotes())
                        .sourceService(inspection.getSourceService())
                        .createdAt(inspection.getCreatedAt())
                        .updatedAt(inspection.getUpdatedAt())
                        .assignedTo(inspection.getAssignedTo())
                        .build());
    }

    @Override
    public Page<InspectionResponse> getPaginatedInspections(int offset, int pageSize) {
        PageRequest pageRequest = PageRequest.of(offset, pageSize);
        return inspectionRepository.findAll(pageRequest)
                .map(inspection -> InspectionResponse.builder()
                        .id(inspection.getId())
                        .applicantName(inspection.getApplicantName())
                        .requestId(inspection.getRequestId())
                        .applicationType(inspection.getApplicationType())
                        .status(inspection.getStatus())
                        .notes(inspection.getNotes())
                        .sourceService(inspection.getSourceService())
                        .createdAt(inspection.getCreatedAt())
                        .updatedAt(inspection.getUpdatedAt())
                        .assignedTo(inspection.getAssignedTo())
                        .build());
    }
}
