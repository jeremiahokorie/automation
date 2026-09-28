package com.automation.core.revenue.controller;

import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.revenue.dto.request.MdaRevenueHeadMappingRequest;
import com.automation.core.revenue.dto.request.RevenueHeadRequest;
import com.automation.core.revenue.dto.response.RevenueHeadResponse;
import com.automation.core.revenue.service.service.RevenueHeadService;
import com.automation.util.constant.AppConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/revenue")
@RequiredArgsConstructor
public class RevenueHeadController {

    private final RevenueHeadService revenueHeadService;

    @PostMapping("/create")
    public ResponseEntity<AppResponse<RevenueHeadResponse>> createRevenueHead(
            @Valid @RequestBody RevenueHeadRequest request) {
        RevenueHeadResponse response = revenueHeadService.createRevenueHead(request);
        return ResponseEntity.ok(AppResponse.<RevenueHeadResponse>builder()
                .message(AppConstant.ApiResponseMessage.UPDATE)
                .status(HttpStatus.OK.value())
                .data(response)
                .error("")
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<AppResponse<List<RevenueHeadResponse>>> getAllRevenueHeads() {
        List<RevenueHeadResponse> response = revenueHeadService.getAllRevenueHeads();
        return ResponseEntity.ok(AppResponse.<List<RevenueHeadResponse>>builder()
                .message(AppConstant.ApiResponseMessage.GET)
                .status(HttpStatus.OK.value())
                .data(response)
                .error("")
                .build());
    }

    @PostMapping("/map-mda")
    public ResponseEntity<AppResponse<String>> mapMdaToRevenueHead(
            @Valid @RequestBody MdaRevenueHeadMappingRequest request) {
        revenueHeadService.mapMdaToRevenueHead(request);

        return ResponseEntity.ok(AppResponse.<String>builder()
                .message("MDA mapped to Revenue Head successfully")
                .status(HttpStatus.OK.value())
                .data(null)
                .error("")
                .build());
    }
}
