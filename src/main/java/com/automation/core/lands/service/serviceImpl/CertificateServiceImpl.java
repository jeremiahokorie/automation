package com.automation.core.lands.service.serviceImpl;

import com.automation.core.lands.dto.response.CertificateResponse;
import com.automation.core.lands.repository.CertificateRepository;
import com.automation.core.lands.service.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;


    @Override
    public List<CertificateResponse> getCert() {
        return List.of();
    }
}
