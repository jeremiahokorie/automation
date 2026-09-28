package com.automation.core.tracking.service;

import com.automation.core.global.model.User;
import com.automation.core.tracking.enums.ActionType;
import com.automation.core.tracking.enums.ApplicationStatus;
import com.automation.core.tracking.dto.response.TrackingApplicationResponse;
import com.automation.core.tracking.dto.response.TrackingDetailsResponse;
import com.automation.core.tracking.dto.response.TrackingSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationTrackingService {

    // Integration methods for MDA services
    String registerApplication(String serviceId, Long applicationId, User user);
    void updateStatus(String trackingReference, ApplicationStatus status, String stage, String officer, String comment);
    void updateStatusByApplicationId(Long applicationId, ApplicationStatus status, String stage, String officer, String comment);
    void requestCitizenAction(String trackingReference, ActionType type, String message);
    void clearCitizenAction(String trackingReference);

    // Query methods for Citizens
    Page<TrackingApplicationResponse> findMyApplications(User user, String search, ApplicationStatus status, String mdaId, String serviceId, Pageable pageable);
    TrackingDetailsResponse getTrackingDetails(String trackingReference, User user);
    TrackingSummaryResponse getTrackingSummary(User user);

    // Filter Data methods
    java.util.List<com.automation.core.mda.dto.response.mdaResponse> getAllMDAs();
    java.util.List<com.automation.core.mda.model.ServicesModel> getServicesByMda(Long mdaId);
}
