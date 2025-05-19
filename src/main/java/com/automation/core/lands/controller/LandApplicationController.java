package com.automation.core.lands.controller;

import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.lands.dto.request.LandApplicationRequest;
import com.automation.core.lands.dto.response.LandApplicationResponse;
import com.automation.core.lands.service.service.LandApplicationService;
import com.automation.util.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lands")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class LandApplicationController {
    private final LandApplicationService landApplicationService;

    @PostMapping("/land")
    public ResponseEntity<AppResponse<LandApplicationResponse>>applyForLand(@RequestBody LandApplicationRequest landApplicationRequest) {
        LandApplicationResponse landApplicationResponse = landApplicationService.applyForLand(landApplicationRequest);
        AppResponse<LandApplicationResponse> lands = AppResponse.<LandApplicationResponse>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(landApplicationResponse).build();
        return new ResponseEntity<>(lands, HttpStatus.OK);
    }

    @GetMapping("/lands")
    public ResponseEntity<AppResponse<List<LandApplicationResponse>>> getAllLands() {
        List<LandApplicationResponse> landApplicationResponse = landApplicationService.getAllApplication();
        return ResponseEntity.ok().body(AppResponse.<List<LandApplicationResponse>>builder()
                .message(AppConstant.ApiResponseMessage.CREATED)
                .status(HttpStatus.OK.value()).data(landApplicationResponse).error("").build());
    }


}
