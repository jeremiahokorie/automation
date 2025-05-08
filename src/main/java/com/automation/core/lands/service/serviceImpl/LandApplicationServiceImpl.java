package com.automation.core.lands.service.serviceImpl;


import com.automation.core.lands.dto.request.LandApplicationRequest;
import com.automation.core.lands.dto.response.LandApplicationResponse;
import com.automation.core.lands.model.LandApplication;
import com.automation.core.lands.repository.LandApplicationRepository;
import com.automation.core.lands.service.service.LandApplicationService;
import com.automation.util.enums.GlobalStatus;
import com.automation.util.enums.LandApplicationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class LandApplicationServiceImpl implements LandApplicationService {

    private final LandApplicationRepository landApplicationRepository;

    @Override
    public LandApplicationResponse applyForLand(LandApplicationRequest landApplicationRequest) {
        LandApplication landApplication = new LandApplication();
        landApplication.setApplicantName(landApplicationRequest.getApplicantName());
        landApplication.setApplicationType(LandApplicationType.CofO);
        landApplication.setApplicationDate(LocalDateTime.now());
        landApplication.setStatus(GlobalStatus.PENDING);
        landApplicationRepository.save(landApplication);
        return LandApplicationResponse.builder()
                .applicationDate(LocalDateTime.now())
                .applicationType(LandApplicationType.CofO)
                .status(GlobalStatus.PENDING)
                .applicantName(landApplicationRequest.getApplicantName()).build();
    }
}
