package com.automation.core.global.verification.controller;

import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.global.verification.dto.VerificationResponse;
import com.automation.core.global.verification.service.GlobalVerificationService;
import com.automation.util.constant.AppConstant;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/global/verify")
@RequiredArgsConstructor
public class GlobalVerificationController {

    private final GlobalVerificationService verificationService;

    @GetMapping("/{documentNumber}")
    @ApiOperation(value = "Unified verification for all government documents",
                   notes = "Verifies a document number across ABSIN, Business, and other government records")
    public ResponseEntity<AppResponse<VerificationResponse>> verify(@PathVariable String documentNumber) {
        VerificationResponse response = verificationService.verifyDocument(documentNumber);

        AppResponse<VerificationResponse> appResponse = AppResponse.<VerificationResponse>builder()
                .message(response.isVerified() ? "Document verified successfully" : "Document not found or invalid")
                .status(response.isVerified() ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .data(response)
                .error(response.isVerified() ? "" : "No matching record found")
                .build();

        return new ResponseEntity<>(appResponse, response.isVerified() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
