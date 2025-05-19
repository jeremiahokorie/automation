package com.automation.core.lands.controller;

import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.lands.dto.response.CertificateResponse;
import com.automation.core.lands.dto.response.LandApplicationResponse;
import com.automation.core.lands.service.service.CertificateService;
import com.automation.util.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lands")
@RequiredArgsConstructor
@CrossOrigin(
        origins = {
                "http://localhost:5174"
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping("/certificates")
    public ResponseEntity<AppResponse<List<CertificateResponse>>> getAllCertificates() {
        List<CertificateResponse> cert = certificateService.getCert();
        return ResponseEntity.ok().body(AppResponse.<List<CertificateResponse>>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(cert).error("").build());
    }



}
