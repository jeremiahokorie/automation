package com.automation.core.lga.controller;

import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.lga.dto.LocalGovernmentRequest;
import com.automation.core.lga.dto.LocalGovernmentResponse;
import com.automation.core.lga.model.LocalGovernment;
import com.automation.core.lga.service.LocalGovernmentService;
import com.automation.util.mapper.GovernanceMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/local-governments")
@RequiredArgsConstructor
public class LocalGovernmentController {

    private final LocalGovernmentService lgaService;

    @GetMapping
    public ResponseEntity<AppResponse<List<LocalGovernmentResponse>>> getAll() {
        List<LocalGovernmentResponse> response = lgaService.findAll().stream()
                .map(lga -> GovernanceMapper.toLgaResponse(lga, false)) // list view: no ward payload
                .toList();
        AppResponse<List<LocalGovernmentResponse>> appResponse = AppResponse.<List<LocalGovernmentResponse>>builder()
                .message("LGAs retrieved successfully")
                .status(HttpStatus.OK.value())
                .data(response)
                .error("")
                .build();
        return ResponseEntity.ok(appResponse);
    }

    // Selecting an LGA returns it together with every ward under it
    @GetMapping("/{id}")
    public ResponseEntity<AppResponse<LocalGovernmentResponse>> getById(@PathVariable String id) {
        LocalGovernment lga = lgaService.findById(id);
        AppResponse<LocalGovernmentResponse> appResponse = AppResponse.<LocalGovernmentResponse>builder()
                .message("LGA retrieved successfully")
                .status(HttpStatus.OK.value())
                .data(GovernanceMapper.toLgaResponse(lga, true)) // include wards
                .error("")
                .build();
        return ResponseEntity.ok(appResponse);
    }

    @PostMapping
    public ResponseEntity<AppResponse<LocalGovernmentResponse>> create(
            @Valid @RequestBody LocalGovernmentRequest request) {
        LocalGovernment created = lgaService.create(request);
        AppResponse<LocalGovernmentResponse> appResponse = AppResponse.<LocalGovernmentResponse>builder()
                .message("LGA created successfully")
                .status(HttpStatus.CREATED.value())
                .data(GovernanceMapper.toLgaResponse(created, false))
                .error("")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(appResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppResponse<LocalGovernmentResponse>> update(
            @PathVariable String id, @Valid @RequestBody LocalGovernmentRequest request) {
        LocalGovernment updated = lgaService.update(id, request);
        AppResponse<LocalGovernmentResponse> appResponse = AppResponse.<LocalGovernmentResponse>builder()
                .message("LGA updated successfully")
                .status(HttpStatus.OK.value())
                .data(GovernanceMapper.toLgaResponse(updated, false))
                .error("")
                .build();

        return ResponseEntity.ok(appResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppResponse<Void>> delete(@PathVariable String id) {
        lgaService.delete(id);
        AppResponse<Void> appResponse = AppResponse.<Void>builder()
                .message("LGA deleted successfully")
                .status(HttpStatus.OK.value())
                .data(null)
                .error("")
                .build();
        return ResponseEntity.ok(appResponse);
    }
}
