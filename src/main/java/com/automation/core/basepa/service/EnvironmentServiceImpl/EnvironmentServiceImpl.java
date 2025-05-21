package com.automation.core.basepa.service.EnvironmentServiceImpl;

import com.automation.core.basepa.dto.request.EnvironmentRequest;
import com.automation.core.basepa.dto.response.EnvironmentResponse;
import com.automation.core.basepa.model.EnvironmentApplication;
import com.automation.core.basepa.repository.EnvironmentRepository;
import com.automation.core.basepa.service.EnvironmentService.EnvironmentService;
import com.automation.util.enums.SourceOfWaste;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class EnvironmentServiceImpl implements EnvironmentService {
    private final EnvironmentRepository environmentRepository;


    @Override
    public EnvironmentResponse apply(EnvironmentRequest environmentRequest) {
        EnvironmentApplication appyPermit = environmentRepository.findByemail(environmentRequest.getEmail());
        if (appyPermit == null) {
            appyPermit = new EnvironmentApplication();
            appyPermit.setEmail(environmentRequest.getEmail());
            appyPermit.setAddress(environmentRequest.getAddress());
            appyPermit.setApplicationDate(LocalDate.now());
            appyPermit.setContactPerson(environmentRequest.getContactPerson());
            appyPermit.setApplicantName(environmentRequest.getApplicantName());
            appyPermit.setFacilityAddress(environmentRequest.getFacilityAddress());
            appyPermit.setFacilityName(environmentRequest.getFacilityName());
            appyPermit.setStatus(Status.PENDING);
            appyPermit.setWasteSource(SourceOfWaste.Household);
            environmentRepository.save(appyPermit);
        }

        return null;
    }
}
