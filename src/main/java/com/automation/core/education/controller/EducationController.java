package com.automation.core.education.controller;

import com.automation.core.education.dto.request.LessonCentreRegistrationRequest;
import com.automation.core.education.dto.request.SchoolRegistrationRequest;
import com.automation.core.education.dto.response.EducationRegistrationResponse;
import com.automation.core.education.dto.response.EducationSummaryResponse;
import com.automation.core.education.service.EducationService;
import com.automation.core.global.model.User;
import com.automation.util.enums.Status;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @PostMapping("/register-school")
    @ApiOperation("Register a new School")
    public ResponseEntity<EducationRegistrationResponse> registerSchool(
            @Valid @RequestBody SchoolRegistrationRequest request) {
        return ResponseEntity.ok(educationService.registerSchool(request));
    }

    @GetMapping("/schools")
    @ApiOperation("Get paginated schools")
    public ResponseEntity<Page<EducationRegistrationResponse>> getSchools(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) Status status) {
        return ResponseEntity.ok(educationService.getSchools(page, size, sortBy, sortDir, status));
    }

    @PostMapping("/register-lesson-centre")
    @ApiOperation("Register a new Lesson Centre")
    public ResponseEntity<EducationRegistrationResponse> registerLessonCentre(
            @Valid @RequestBody LessonCentreRegistrationRequest request) {
        return ResponseEntity.ok(educationService.registerLessonCentre(request));
    }

    @GetMapping("/lesson-centres")
    @ApiOperation("Get paginated lesson centres")
    public ResponseEntity<Page<EducationRegistrationResponse>> getLessonCentres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) Status status) {
        return ResponseEntity.ok(educationService.getLessonCentres(page, size, sortBy, sortDir, status));
    }

    @GetMapping("/summary")
    @ApiOperation("Get education registration summary")
    public ResponseEntity<EducationSummaryResponse> getSummary() {
        return ResponseEntity.ok(educationService.getEducationSummary());
    }

    @PutMapping("/review/{id}")
    @ApiOperation("Update application status (Admin only)")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam Status status,
            @RequestParam String officer,
            @RequestParam String comment) {
        educationService.updateSchoolStatus(id, status, officer, comment);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/approve/{id}")
    @ApiOperation("Final approval and licence issuance (Admin only)")
    public ResponseEntity<Void> approve(
            @PathVariable Long id,
            @RequestParam String permitUrl) {
        educationService.approveSchool(id, permitUrl);
        return ResponseEntity.ok().build();
    }
}
