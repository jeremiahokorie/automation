package com.automation.core.education.controller;

import com.automation.core.education.dto.request.LessonCentreRegistrationRequest;
import com.automation.core.education.dto.request.SchoolRegistrationRequest;
import com.automation.core.education.dto.response.EducationRegistrationResponse;
import com.automation.core.education.service.EducationService;
import com.automation.core.global.model.User;
import com.automation.util.enums.Status;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
            @RequestBody SchoolRegistrationRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(educationService.registerSchool(request, user));
    }

    @PostMapping("/register-lesson-centre")
    @ApiOperation("Register a new Lesson Centre")
    public ResponseEntity<EducationRegistrationResponse> registerLessonCentre(
            @RequestBody LessonCentreRegistrationRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(educationService.registerLessonCentre(request, user));
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
