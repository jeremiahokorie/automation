package com.automation.core.lands.controller;

import com.automation.core.basepa.dto.request.ApprovalRequest;
import com.automation.core.basepa.dto.response.ApprovalResponse;
import com.automation.core.commerce.dto.response.BusinessRegistrationResponse;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.lands.dto.response.StatutoryAllocationResponse;
import com.automation.core.lands.model.StatutoryAllocation;
import com.automation.core.lands.service.service.StatutoryAllocationService;
import com.automation.util.constant.AppConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lands/statutory-allocation")
@RequiredArgsConstructor
public class StatutoryAllocationController {
    private final StatutoryAllocationService statutoryAllocationService;



    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadDocuments(@RequestParam String applicantName, @RequestParam Map<String, MultipartFile> documents) {
        try {
            Map<String, String> response = statutoryAllocationService.uploadDocuments(applicantName, documents);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to upload documents");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<AppResponse<List<StatutoryAllocationResponse>>> getAllAllocations() {
        List<StatutoryAllocationResponse> allocations = statutoryAllocationService.getAllAllocations();
        return ResponseEntity.ok().body(AppResponse.<List<StatutoryAllocationResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value()).data(allocations).error("").build());
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<AppResponse<ApprovalResponse>> approvePermit(
            @PathVariable Long id,
            @Valid @RequestBody ApprovalRequest commentRequest) {

        ApprovalResponse approval = statutoryAllocationService.approveStatutory(id, commentRequest);

        AppResponse<ApprovalResponse> response = AppResponse.<ApprovalResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value())
                .data(approval)
                .error("")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<AppResponse<ApprovalResponse>> rejectBusiness(
            @PathVariable Long id,
            @RequestBody ApprovalRequest commentRequest) {

        ApprovalResponse reject = statutoryAllocationService.rejectStatutory(id, commentRequest);

        AppResponse<ApprovalResponse> response = AppResponse.<ApprovalResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value())
                .data(reject)
                .error("")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
