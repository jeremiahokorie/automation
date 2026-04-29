package com.automation.core.inspection.controller;


import com.automation.core.basepa.dto.response.EnvironmentResponse;
import com.automation.core.commerce.dto.request.ApprovalandRejectRequest;
import com.automation.core.commerce.dto.response.ApprovalandRejectResponse;
import com.automation.core.commerce.dto.response.BusinessRegistrationResponse;
import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.inspection.dto.request.InspectionRequest;
import com.automation.core.inspection.dto.request.StatusUpdateDto;
import com.automation.core.inspection.dto.response.InspectionResponse;
import com.automation.core.inspection.service.InspectionService.InspectionService;
import com.automation.util.constant.AppConstant;
import com.automation.util.enums.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/inspection")
@RequiredArgsConstructor
public class InspectionController {
    private final InspectionService inspectionService;


    @PostMapping("create-inspection")
    @ApiOperation(value = "create a new inspection request",
            notes = "This endpoint creates a new inspection request")
    public ResponseEntity<AppResponse<InspectionResponse>>createInspection(@RequestBody InspectionRequest inspectionRequest){
        InspectionResponse inspectionResponse = inspectionService.createInspection(inspectionRequest);
        AppResponse<InspectionResponse> appResponse = AppResponse.<InspectionResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(inspectionResponse).error("").build();
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @GetMapping("/pending-inspection")
    @ApiOperation(value = "get all pending inspection requests",
            notes = "This endpoint returns all pending inspection requests")
    public ResponseEntity<AppResponse<List<InspectionResponse>>> getPendingInspection() {
       List<InspectionResponse> responses = inspectionService.getAllInspectionRequest();
       return ResponseEntity.ok().body(AppResponse.<List<InspectionResponse>>builder()
                       .message(AppConstant.ApiResponseMessage.GET)
                       .status(HttpStatus.OK.value()).data(responses).error("").build());
    }

    @GetMapping("/paginated/inspection")
    @ApiOperation(value = "get all inspection requests with pagination",
            notes = "This endpoint returns all inspection requests with pagination")
    public ResponseEntity<AppResponse<Page<InspectionResponse>>> getPaginatedInspection(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Page<InspectionResponse> pages = inspectionService.getPaginatedInspection(page, size, sortBy, sortDir);
        AppResponse<Page<InspectionResponse>> response = AppResponse.<Page<InspectionResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value())
                .data(pages)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated/inspection/{offset}/{pageSize}")
    @ApiOperation(value = "get all inspection requests with pagination",
            notes = "This endpoint returns all inspection requests with pagination")
    public ResponseEntity<AppResponse<Page<InspectionResponse>>>getPaginatedInspections(@PathVariable int offset, @PathVariable int pageSize){
        Page<InspectionResponse> pages = inspectionService.getPaginatedInspections(offset, pageSize);
        AppResponse<Page<InspectionResponse>> response = AppResponse.<Page<InspectionResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .recordCount(pages.getSize())
                .status(HttpStatus.OK.value())
                .data(pages)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    @ApiOperation(value = "update inspection status",
            notes = "This endpoint updates the status of an inspection request")
    public ResponseEntity<AppResponse<InspectionResponse>> updateStatus(@PathVariable Long id, @RequestBody StatusUpdateDto dto) {
        InspectionResponse inspection = inspectionService.updateInspectionStatus(id,dto);
        AppResponse<InspectionResponse>response = AppResponse.<InspectionResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value()).data(inspection).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
