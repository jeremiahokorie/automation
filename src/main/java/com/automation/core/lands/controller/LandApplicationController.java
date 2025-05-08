package com.automation.core.lands.controller;

import com.automation.core.global.dto.response.AppResponse;
import com.automation.core.lands.dto.request.LandApplicationRequest;
import com.automation.core.lands.dto.response.LandApplicationResponse;
import com.automation.core.lands.service.service.LandApplicationService;
import com.automation.util.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lands")
@RequiredArgsConstructor
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


}
