package com.automation.core.lga.controller;

import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.lga.dto.WardRequest;
import com.automation.core.lga.dto.WardResponse;
import com.automation.core.lga.model.Ward;
import com.automation.core.lga.service.WardService;
import com.automation.util.mapper.GovernanceMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WardController {

    private final WardService wardService;

    // GET /api/v1/local-governments/{lgaId}/wards -> all wards under that LGA
    @GetMapping("/local-governments/{lgaId}/wards")
    public ResponseEntity<AppResponse<List<WardResponse>>> getWardsByLga(@PathVariable String lgaId) {
        List<WardResponse> wards = wardService.findByLocalGovernment(lgaId).stream()
                .map(GovernanceMapper::toWardResponse)
                .toList();
        AppResponse <List<WardResponse>> response = AppResponse.<List<WardResponse>>builder()
                .message("Wards retrieved successfully")
                .status(HttpStatus.OK.value())
                .data(wards)
                .error("")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/local-governments/{lgaId}/wards")
    public ResponseEntity<AppResponse<WardResponse>> create(
            @PathVariable String lgaId, @Valid @RequestBody WardRequest request) {
        Ward ward = wardService.create(lgaId, request);
        AppResponse <WardResponse> response = AppResponse.<WardResponse>builder()
                .message("Ward created successfully")
                .status(HttpStatus.CREATED.value())
                .data(GovernanceMapper.toWardResponse(ward))
                .error("")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/wards/{wardId}")
    public ResponseEntity<AppResponse<WardResponse>> update(
            @PathVariable String wardId, @Valid @RequestBody WardRequest request) {
        Ward updated = wardService.update(wardId, request);
        AppResponse <WardResponse> response = AppResponse.<WardResponse>builder()
                .message("Ward updated successfully")
                .status(HttpStatus.OK.value())
                .data(GovernanceMapper.toWardResponse(updated))
                .error("")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/wards/{wardId}")
    public ResponseEntity<AppResponse<Void>> delete(@PathVariable String wardId) {
        wardService.delete(wardId);
        AppResponse <Void> response = AppResponse.<Void>builder()
                .message("Ward deleted successfully")
                .status(HttpStatus.OK.value())
                .data(null)
                .error("")
                .build();
        return ResponseEntity.ok(response);
    }
}