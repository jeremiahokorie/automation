package com.automation.core.inspection.controller;


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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<AppResponse<InspectionResponse>>createInspection(@RequestBody InspectionRequest inspectionRequest){
        InspectionResponse inspectionResponse = inspectionService.createInspection(inspectionRequest);
        AppResponse<InspectionResponse> appResponse = AppResponse.<InspectionResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(inspectionResponse).error("").build();
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @GetMapping("/pending-inspection")
    public ResponseEntity<AppResponse<List<InspectionResponse>>> getPendingInspection() {
       List<InspectionResponse> responses = inspectionService.getAllInspectionRequest();
       return ResponseEntity.ok().body(AppResponse.<List<InspectionResponse>>builder()
                       .message(AppConstant.ApiResponseMessage.GET)
                       .status(HttpStatus.OK.value()).data(responses).error("").build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AppResponse<InspectionResponse>> updateStatus(@PathVariable Long id, @RequestBody StatusUpdateDto dto) {
        InspectionResponse inspection = inspectionService.updateInspectionStatus(id,dto);
        AppResponse<InspectionResponse>response = AppResponse.<InspectionResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value()).data(inspection).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}