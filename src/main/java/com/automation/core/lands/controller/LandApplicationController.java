package com.automation.core.lands.controller;

import com.automation.core.basepa.dto.response.EnvironmentSummaryResponse;
import com.automation.core.commerce.dto.response.LandApplicationSummaryResponse;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.lands.dto.request.CustomaryAllocationRequest;
import com.automation.core.lands.dto.request.LandApplicationRequest;
import com.automation.core.lands.dto.request.StatutoryApplicationRequest;
import com.automation.core.lands.dto.response.CustomaryAllocationResponse;
import com.automation.core.lands.dto.response.LandApplicationResponse;
import com.automation.core.lands.dto.response.StatutoryAllocationResponse;
import com.automation.core.lands.dto.response.StatutoryApplicationResponse;
import com.automation.core.lands.service.service.LandApplicationService;
import com.automation.util.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/land")
@RequiredArgsConstructor
public class LandApplicationController {
    private final LandApplicationService landApplicationService;


    @PostMapping("/submit-customary-form")
    public ResponseEntity<?> submitForm(@RequestBody CustomaryAllocationRequest formRequest) {
        Long formId = landApplicationService.saveFormRequest(formRequest);
        return ResponseEntity.ok(Map.of("formId", formId));
    }


    @PostMapping(value = "/upload-customary-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFiles(
            @RequestParam("formId") Long formId,
            @RequestPart(value = "passportPhoto", required = false) MultipartFile passportPhoto,
            @RequestPart(value = "taxClearance", required = false) MultipartFile taxClearance,
            @RequestPart(value = "affidavit", required = false) MultipartFile affidavit,
            @RequestPart(value = "communityConsentLetter", required = false) MultipartFile communityConsentLetter,
            @RequestPart(value = "developmentSketch", required = false) MultipartFile developmentSketch
    ) throws IOException {
        landApplicationService.uploadFilesCustomary(formId, passportPhoto, taxClearance, affidavit, communityConsentLetter, developmentSketch);
        return ResponseEntity.ok("Files uploaded successfully");
    }

    @PostMapping(value = "/customary")
    public ResponseEntity<Map<String, String>> uploadDocuments(
            @RequestBody CustomaryAllocationRequest customaryAllocationRequest,
            @RequestParam Map<String, MultipartFile> documents
    ) {
        try {
            Map<String, String> response = landApplicationService.customLandApplication(customaryAllocationRequest, documents);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to upload documents");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


   // @PreAuthorize("isAuthenticated()")
    @PostMapping("/statutory")
    public ResponseEntity<Map<String, String>> statutoryLandApplication(@RequestBody StatutoryApplicationRequest statutoryApplicationRequest, @RequestParam Map<String, MultipartFile> documents) throws IOException {
        try {
            Map<String, String> response = landApplicationService.statutoryallocation(statutoryApplicationRequest, documents);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to upload documents");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

       // return ResponseEntity.ok(response);


    }

    @GetMapping("/all-statutory-allocations")
    public ResponseEntity<AppResponse<List<CustomaryAllocationResponse>>> getAllCustomaryAllocations() {
        List<CustomaryAllocationResponse> allocations = landApplicationService.getAllCustomaryAllocations();
        return ResponseEntity.ok().body(AppResponse.<List<CustomaryAllocationResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(allocations).error("").build());
    }

    @GetMapping("/all-customary-allocations")
    public ResponseEntity<AppResponse<List<StatutoryApplicationResponse>>> getAllAllocations() {
        List<StatutoryApplicationResponse> allocations = landApplicationService.getAllStatutoryAllocations();
        return ResponseEntity.ok().body(AppResponse.<List<StatutoryApplicationResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(allocations).error("").build());
    }


    @GetMapping("/statutory-summary")
    public ResponseEntity<LandApplicationSummaryResponse>getAllsummary(){
        LandApplicationSummaryResponse landApp = landApplicationService.getAllStatutorySummary();
        return ResponseEntity.ok().body(landApp);
    }

    @GetMapping("/customary-summary")
    public ResponseEntity<LandApplicationSummaryResponse>summary(){
        LandApplicationSummaryResponse landApp = landApplicationService.getAllCustomarySummary();
        return ResponseEntity.ok().body(landApp);
    }
}
