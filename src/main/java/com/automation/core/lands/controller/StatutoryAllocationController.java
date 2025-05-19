package com.automation.core.lands.controller;

import com.automation.core.lands.service.service.StatutoryAllocationService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/lands/statutory-allocation")
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
public class StatutoryAllocationController {
    private final StatutoryAllocationService statutoryAllocationService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadDocuments(@RequestParam Map<String, MultipartFile> documents) throws IOException {
        Map<String, String> response = statutoryAllocationService.uploadDocuments(documents);
        return ResponseEntity.ok(response);
    }







}
