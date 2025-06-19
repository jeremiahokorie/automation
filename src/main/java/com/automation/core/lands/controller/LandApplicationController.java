package com.automation.core.lands.controller;

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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/customary")
    public ResponseEntity<Map<String, String>> customLandApplication(@RequestBody CustomaryAllocationRequest customaryAllocationRequest, @RequestParam Map<String, MultipartFile> documents) throws IOException {
        Map<String, String> response = landApplicationService.customLandApplication(customaryAllocationRequest, documents);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/statutory")
    public ResponseEntity<Map<String, String>> statutoryLandApplication(@RequestBody StatutoryApplicationRequest statutoryApplicationRequest, @RequestParam Map<String, MultipartFile> documents) throws IOException {
        Map<String, String> response = landApplicationService.statutoryallocation(statutoryApplicationRequest, documents);
        return ResponseEntity.ok(response);
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

//    @PostMapping("/land")
//    public ResponseEntity<AppResponse<LandApplicationResponse>>applyForLand(@RequestBody LandApplicationRequest landApplicationRequest) {
//        LandApplicationResponse landApplicationResponse = landApplicationService.applyForLand(landApplicationRequest);
//        AppResponse<LandApplicationResponse> lands = AppResponse.<LandApplicationResponse>builder()
//                .message(AppConstant.ApiResponseMessage.CREATED)
//                .status(HttpStatus.OK.value()).data(landApplicationResponse).build();
//        return new ResponseEntity<>(lands, HttpStatus.OK);
//    }
//
//    @GetMapping("/lands")
//    public ResponseEntity<AppResponse<List<LandApplicationResponse>>> getAllLands() {
//        List<LandApplicationResponse> landApplicationResponse = landApplicationService.getAllApplication();
//        return ResponseEntity.ok().body(AppResponse.<List<LandApplicationResponse>>builder()
//                .message(AppConstant.ApiResponseMessage.CREATED)
//                .status(HttpStatus.OK.value()).data(landApplicationResponse).error("").build());
//    }



}
