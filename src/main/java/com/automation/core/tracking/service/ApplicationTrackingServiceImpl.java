package com.automation.core.tracking.service;

import com.automation.core.global.model.User;
import com.automation.core.tracking.dto.response.*;
import com.automation.core.tracking.enums.ActionType;
import com.automation.core.tracking.enums.ApplicationStatus;
import com.automation.core.tracking.model.ApplicationTracking;
import com.automation.core.tracking.model.ApplicationTrackingHistory;
import com.automation.core.tracking.repository.ApplicationTrackingHistoryRepository;
import com.automation.core.tracking.repository.ApplicationTrackingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationTrackingServiceImpl implements ApplicationTrackingService {

    private final ApplicationTrackingRepository trackingRepository;
    private final ApplicationTrackingHistoryRepository historyRepository;
    private final com.automation.core.mda.service.service.mdaService mdaService;

    @Override
    public String registerApplication(String serviceId, Long applicationId, User user) {
        log.info("Registering application {} for service {} user {}", applicationId, serviceId, user.getEmail());

        String reference = generateTrackingReference();

        ApplicationTracking tracking = ApplicationTracking.builder()
                .trackingReference(reference)
                .serviceId(serviceId)
                .applicationId(applicationId)
                .user(user)
                .currentStatus(ApplicationStatus.PENDING)
                .currentStage("APPLICATION_SUBMITTED")
                .createdAt(LocalDateTime.now())
                .build();

        ApplicationTracking saved = trackingRepository.save(tracking);

        // Create initial history entry
        createHistoryEntry(saved, ApplicationStatus.PENDING, "APPLICATION_SUBMITTED", "System", "Application registered in tracking system");

        return saved.getTrackingReference();
    }

    @Override
    public void updateStatus(String trackingReference, ApplicationStatus status, String stage, String officer, String comment) {
        ApplicationTracking tracking = trackingRepository.findByTrackingReference(trackingReference)
                .orElseThrow(() -> new RuntimeException("Tracking record not found: " + trackingReference));

        ApplicationStatus oldStatus = tracking.getCurrentStatus();
        String oldStage = tracking.getCurrentStage();

        tracking.setCurrentStatus(status);
        tracking.setCurrentStage(stage);
        tracking.setUpdatedAt(LocalDateTime.now());

        trackingRepository.save(tracking);
        createHistoryEntry(tracking, status, stage, officer, comment);
        log.info("Updated tracking {}: Status {} -> {}, Stage {} -> {}", trackingReference, oldStatus, status, oldStage, stage);
    }

    @Override
    public void updateStatusByApplicationId(Long applicationId, ApplicationStatus status, String stage, String officer, String comment) {
        ApplicationTracking tracking = trackingRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException("Tracking record not found for application ID: " + applicationId));

        updateStatus(tracking.getTrackingReference(), status, stage, officer, comment);
    }

    @Override
    public void requestCitizenAction(String trackingReference, ActionType type, String message) {
        ApplicationTracking tracking = trackingRepository.findByTrackingReference(trackingReference)
                .orElseThrow(() -> new RuntimeException("Tracking record not found: " + trackingReference));

        tracking.setActionRequired(true);
        tracking.setActionType(type);
        tracking.setActionMessage(message);

        trackingRepository.save(tracking);
        createHistoryEntry(tracking, tracking.getCurrentStatus(), tracking.getCurrentStage(), "System", "Action required: " + type);
    }

    @Override
    public void clearCitizenAction(String trackingReference) {
        ApplicationTracking tracking = trackingRepository.findByTrackingReference(trackingReference)
                .orElseThrow(() -> new RuntimeException("Tracking record not found: " + trackingReference));

        tracking.setActionRequired(false);
        tracking.setActionMessage(null);
        tracking.setActionType(null);

        trackingRepository.save(tracking);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrackingApplicationResponse> findMyApplications(User user, String search, ApplicationStatus status, String mdaId, String serviceId, Pageable pageable) {
        Specification<ApplicationTracking> spec = Specification.where(null);

        if (user != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("user"), user));
        }

        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("currentStatus"), status));
        }

        if (mdaId != null && !mdaId.isBlank()) {
            // Currently, ApplicationTracking only has serviceId.
            // We need to filter applications where serviceId corresponds to any service belonging to this MDA.
            // Since ApplicationTracking is a flat table, we check if serviceId is in the list of service names for this MDA.
            java.util.List<com.automation.core.mda.model.ServicesModel> services = mdaService.getServicesByMda(Long.parseLong(mdaId));
            java.util.List<String> serviceNames = services.stream()
                    .map(com.automation.core.mda.model.ServicesModel::getName)
                    .collect(java.util.stream.Collectors.toList());

            spec = spec.and((root, query, cb) -> root.get("serviceId").in(serviceNames));
        }

        if (serviceId != null && !serviceId.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("serviceId"), serviceId));
        }

        if (search != null && !search.isBlank()) {
            String pattern = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                cb.like(cb.lower(root.get("trackingReference")), pattern)
            );
        }

        return trackingRepository.findAll(spec, pageable).map(this::mapToSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<com.automation.core.mda.dto.response.mdaResponse> getAllMDAs() {
        return mdaService.getMdas();
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<com.automation.core.mda.model.ServicesModel> getServicesByMda(Long mdaId) {
        return mdaService.getServicesByMda(mdaId);
    }

    @Override
    @Transactional(readOnly = true)
    public TrackingDetailsResponse getTrackingDetails(String trackingReference, User user) {
        ApplicationTracking tracking = trackingRepository.findByTrackingReference(trackingReference)
                .orElseThrow(() -> new RuntimeException("Tracking record not found"));

        // Security check: Ownership verification
        if (!tracking.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to tracking record");
        }

        List<ApplicationTrackingHistory> history = historyRepository.findByTrackingIdOrderByCreatedAtAsc(tracking.getId());

        return TrackingDetailsResponse.builder()
                .trackingReference(tracking.getTrackingReference())
                .serviceName(tracking.getServiceId())
                .status(tracking.getCurrentStatus())
                .currentStage(tracking.getCurrentStage())
                .submittedAt(tracking.getCreatedAt())
                .lastUpdatedAt(tracking.getUpdatedAt())
                .actionRequired(tracking.isActionRequired())
                .actionMessage(tracking.getActionMessage())
                .history(history.stream().map(this::mapToHistoryResponse).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public TrackingSummaryResponse getTrackingSummary(User user) {
        List<ApplicationTracking> apps = trackingRepository.findAll((root, query, cb) -> cb.equal(root.get("user"), user));

        long total = apps.size();
        long actionRequired = apps.stream().filter(ApplicationTracking::isActionRequired).count();

        Map<String, Long> counts = apps.stream()
                .collect(Collectors.groupingBy(a -> a.getCurrentStatus().name(), Collectors.counting()));

        return TrackingSummaryResponse.builder()
                .total(total)
                .statusCounts(counts)
                .actionRequiredCount(actionRequired)
                .build();
    }

    private void createHistoryEntry(ApplicationTracking tracking, ApplicationStatus status, String stage, String officer, String comment) {
        ApplicationTrackingHistory history = ApplicationTrackingHistory.builder()
                .tracking(tracking)
                .status(status)
                .stage(stage)
                .changedBy(officer)
                .comment(comment)
                .createdAt(LocalDateTime.now())
                .build();
        historyRepository.save(history);
    }

    private String generateTrackingReference() {
        return "TRK-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private TrackingApplicationResponse mapToSummaryResponse(ApplicationTracking tracking) {
        return TrackingApplicationResponse.builder()
                .trackingReference(tracking.getTrackingReference())
                .serviceName(tracking.getServiceId())
                .status(tracking.getCurrentStatus())
                .currentStage(tracking.getCurrentStage())
                .actionRequired(tracking.isActionRequired())
                .updatedAt(tracking.getUpdatedAt())
                .build();
    }

    private TrackingHistoryResponse mapToHistoryResponse(ApplicationTrackingHistory history) {
        return TrackingHistoryResponse.builder()
                .status(history.getStatus())
                .stage(history.getStage())
                .changedBy(history.getChangedBy())
                .comment(history.getComment())
                .changedAt(history.getCreatedAt())
                .build();
    }
}
