package com.automation.core.wardactivity.service;

import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.core.global.model.Roles;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.lga.model.Ward;
import com.automation.core.lga.repository.WardRepository;
import com.automation.core.wardactivity.dto.request.CreateActivityReportRequest;
import com.automation.core.wardactivity.dto.request.CreateWardRequest;
import com.automation.core.wardactivity.dto.request.UpdateActivityReportRequest;
import com.automation.core.wardactivity.dto.response.ActivityReportResponse;
import com.automation.core.wardactivity.dto.response.WardSimpleResponse;
import com.automation.core.wardactivity.enums.ActivityCategory;
import com.automation.core.wardactivity.enums.ActivityStatus;
import com.automation.core.wardactivity.model.ActivityReport;
import com.automation.core.wardactivity.repository.ActivityReportRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Service
public class WardActivityServiceImpl implements WardActivityService {

    private static final Set<String> REPRESENTATIVE_ROLES = Set.of("REPRESENTATIVE", "SUPERADMIN", "ADMIN");
    private static final Set<String> WARD_LEADER_ROLES = Set.of("WARD_LEADER","USER");

    private final ActivityReportRepository activityReportRepository;
    private final UserRepository userRepository;
    private final WardRepository wardRepository;

    public WardActivityServiceImpl(ActivityReportRepository activityReportRepository,
                                   UserRepository userRepository,
                                   WardRepository wardRepository) {
        this.activityReportRepository = activityReportRepository;
        this.userRepository = userRepository;
        this.wardRepository = wardRepository;
    }

    @Override
    public WardSimpleResponse createWard(CreateWardRequest request, String actorEmail) {
        User actor = getUser(actorEmail);
        if (!isRepresentative(actor)) {
            throw new AccessDeniedException("Only representative can create wards");
        }
        throw new UnsupportedOperationException("Ward creation here requires local government linkage and should use existing LGA flow");
    }

    @Override
    public Page<WardSimpleResponse> getWards(Pageable pageable, String actorEmail) {
        getUser(actorEmail);
        return wardRepository.findAll(pageable).map(this::toWardSimple);
    }

    @Override
    public ActivityReportResponse createReport(CreateActivityReportRequest request, String actorEmail) {
        User actor = getUser(actorEmail);
        if (!isWardLeader(actor)) {
            throw new AccessDeniedException("Only ward leaders can create reports");
        }

        Ward ward = resolveUserWard(actor);
        ActivityReport report = new ActivityReport();
        report.setWard(ward);
        report.setSubmittedBy(actor);
        report.setDate(request.getDate());
        report.setCategory(request.getCategory());
        report.setTitle(request.getTitle());
        report.setDescription(request.getDescription());
        report.setStatus(request.getStatus());

        return toResponse(activityReportRepository.save(report));
    }

    @Override
    public Page<ActivityReportResponse> getReports(String actorEmail, String wardId, ActivityStatus status, ActivityCategory category, LocalDate from, LocalDate to, Pageable pageable) {
        User actor = getUser(actorEmail);

        if (isRepresentative(actor)) {
            return activityReportRepository
                    .searchAll(wardId, status, category, from, to, pageable)
                    .map(this::toResponse);
        }

        Ward ward = resolveUserWard(actor);
        return activityReportRepository
                .searchByWard(ward.getId(), status, category, from, to, pageable)
                .map(this::toResponse);
    }

    @Override
    public ActivityReportResponse getReportById(Long id, String actorEmail) {
        User actor = getUser(actorEmail);
        ActivityReport report = getReport(id);

        if (isRepresentative(actor)) {
            return toResponse(report);
        }

        Ward ward = resolveUserWard(actor);
        if (!report.getWard().getId().equals(ward.getId())) {
            throw new AccessDeniedException("You cannot view reports outside your ward");
        }
        return toResponse(report);
    }

    @Override
    public ActivityReportResponse updateReport(Long id, UpdateActivityReportRequest request, String actorEmail) {
        User actor = getUser(actorEmail);
        ActivityReport report = getReport(id);

        if (isRepresentative(actor)) {
            if (request.getStatus() == null) {
                throw new AccessDeniedException("Representative can only update status");
            }
            report.setStatus(request.getStatus());
            return toResponse(activityReportRepository.save(report));
        }

        Ward ward = resolveUserWard(actor);
        if (!report.getWard().getId().equals(ward.getId())) {
            throw new AccessDeniedException("You cannot update reports outside your ward");
        }

        if (request.getDate() != null) report.setDate(request.getDate());
        if (request.getCategory() != null) report.setCategory(request.getCategory());
        if (request.getTitle() != null && !request.getTitle().isBlank()) report.setTitle(request.getTitle());
        if (request.getDescription() != null && !request.getDescription().isBlank()) report.setDescription(request.getDescription());
        if (request.getStatus() != null) report.setStatus(request.getStatus());

        return toResponse(activityReportRepository.save(report));
    }

    @Override
    public Map<String, Object> getDashboardSummary(String actorEmail) {
        User actor = getUser(actorEmail);
        if (!isRepresentative(actor)) {
            throw new AccessDeniedException("Only representative can view dashboard summary");
        }

        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
        LocalDate startOfMonth = now.withDayOfMonth(1);

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalThisWeek", activityReportRepository.countByDateGreaterThanEqual(startOfWeek));
        summary.put("totalThisMonth", activityReportRepository.countByDateGreaterThanEqual(startOfMonth));
        summary.put("reportsPerWard", activityReportRepository.countReportsPerWard());
        summary.put("needsAttentionPerWard", activityReportRepository.countNeedsAttentionPerWard(ActivityStatus.NEEDS_ATTENTION));
        return summary;
    }

    private ActivityReport getReport(Long id) {
        return activityReportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Report not found"));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }

    private boolean isRepresentative(User user) {
        return user.getRoles() != null && user.getRoles().stream().map(Roles::getValue).anyMatch(REPRESENTATIVE_ROLES::contains);
    }

    private boolean isWardLeader(User user) {
        return user.getRoles() != null && user.getRoles().stream().map(Roles::getValue).anyMatch(WARD_LEADER_ROLES::contains);
    }

    private Ward resolveUserWard(User user) {
        String wardRef = user.getAddress();
        if (wardRef == null || wardRef.isBlank()) {
            throw new AccessDeniedException("Ward leader account is not linked to a ward");
        }

        return wardRepository.findById(wardRef).orElseGet(() ->
                wardRepository.findAll().stream()
                        .filter(w -> w.getName() != null && w.getName().equalsIgnoreCase(wardRef))
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Linked ward not found. Set user.address to ward id or exact ward name."
                        ))
        );
    }

    private WardSimpleResponse toWardSimple(Ward ward) {
        WardSimpleResponse response = new WardSimpleResponse();
        response.setId(ward.getId());
        response.setName(ward.getName());
        response.setCode(ward.getId());
        return response;
    }

    private ActivityReportResponse toResponse(ActivityReport report) {
        ActivityReportResponse response = new ActivityReportResponse();
        response.setId(report.getId());
        response.setWardId(report.getWard().getId());
        response.setWardName(report.getWard().getName());
        response.setSubmittedByUserId(report.getSubmittedBy().getId());
        response.setSubmittedByName(report.getSubmittedBy().getFirstName() + " " + report.getSubmittedBy().getLastName());
        response.setDate(report.getDate());
        response.setCategory(report.getCategory());
        response.setTitle(report.getTitle());
        response.setDescription(report.getDescription());
        response.setStatus(report.getStatus());
        response.setCreatedAt(report.getCreatedAt());
        response.setUpdatedAt(report.getUpdatedAt());
        return response;
    }
}
