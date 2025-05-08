package com.automation.core.lands.controller;

import com.automation.core.lands.service.service.StatutoryAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lands")
@RequiredArgsConstructor
public class StatutoryAllocationController {
    private final StatutoryAllocationService statutoryAllocationService;





}
