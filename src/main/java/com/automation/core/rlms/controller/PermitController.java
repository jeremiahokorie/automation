package com.automation.core.rlms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/permit/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PermitController {

}
