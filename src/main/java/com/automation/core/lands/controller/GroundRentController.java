package com.automation.core.lands.controller;

import com.automation.core.lands.model.GroundRent;
import com.automation.core.lands.repository.GroundRentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/lands/ground-rent")
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
public class GroundRentController {

    @Autowired
    private GroundRentRepository repository;
    private static final String UPLOAD_DIR = "/opt/uploads/ground-rent/";

    @PostMapping("/submit")
    public ResponseEntity<GroundRent> submitGroundRent(@RequestParam String baNo,
                                                       @RequestParam String landNo,
                                                       @RequestParam String record,
                                                       @RequestParam Double rent,
                                                       @RequestParam(required = false) MultipartFile file) throws Exception {
        GroundRent groundRent = new GroundRent();
        groundRent.setBaNo(baNo);
        groundRent.setLandNo(landNo);
        groundRent.setRecord(record);
        groundRent.setRent(rent);
        if (file != null && !file.isEmpty()) {
            String filePath = UPLOAD_DIR + file.getOriginalFilename();
            Files.copy(file.getInputStream(), Path.of(filePath));
            groundRent.setOptionalFile(filePath);
        }
        repository.save(groundRent);
        return ResponseEntity.ok(groundRent);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroundRent>> getAllGroundRents() {
        return ResponseEntity.ok(repository.findAll());
    }
}
