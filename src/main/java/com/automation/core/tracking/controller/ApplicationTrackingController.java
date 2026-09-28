package com.automation.core.tracking.controller;

import com.automation.core.global.model.User;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.tracking.dto.response.*;
import com.automation.core.tracking.enums.ApplicationStatus;
import com.automation.core.tracking.service.ApplicationTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracking")
@RequiredArgsConstructor
public class ApplicationTrackingController {

    private final ApplicationTrackingService trackingService;
    private final UserRepository userRepository;

    @GetMapping("/my-applications")
    public ResponseEntity<Page<TrackingApplicationResponse>> getMyApplications(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) String mdaId,
            @RequestParam(required = false) String serviceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        User user = getCurrentUser();
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(trackingService.findMyApplications(user, search, status, mdaId, serviceId, pageable));
    }

    @GetMapping("/{trackingReference}")
    public ResponseEntity<TrackingDetailsResponse> getDetails(@PathVariable String trackingReference) {
        User user = getCurrentUser();
        return ResponseEntity.ok(trackingService.getTrackingDetails(trackingReference, user));
    }

    @GetMapping("/summary")
    public ResponseEntity<TrackingSummaryResponse> getSummary() {
        User user = getCurrentUser();
        return ResponseEntity.ok(trackingService.getTrackingSummary(user));
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in database"));
    }

}
