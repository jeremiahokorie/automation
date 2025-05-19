package com.automation.core.lands.controller;

import com.automation.core.lands.model.CertificateOfOccupancy;
import com.automation.core.lands.service.service.CertificateOfOccupancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lands/certificate-of-occupancy")
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
public class CertificateOfOccupancyController {

    @Autowired
    private CertificateOfOccupancyService certificateOfOccupancyService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadDocuments(@RequestParam String applicantName, @RequestParam Map<String, MultipartFile> documents) {
        try {
            Map<String, String> response = certificateOfOccupancyService.uploadDocuments(applicantName, documents);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to upload documents");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CertificateOfOccupancy>> getAllCertificates() {
        List<CertificateOfOccupancy> certificates = certificateOfOccupancyService.getAllCertificates();
        return ResponseEntity.ok(certificates);
    }

}
