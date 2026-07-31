package com.automation.core.lga.service;

import com.automation.core.global.exception.DuplicateResourceException;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.core.lga.dto.WardRequest;
import com.automation.core.lga.model.LocalGovernment;
import com.automation.core.lga.model.Ward;
import com.automation.core.lga.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WardService {

    private final WardRepository wardRepository;
    private final LocalGovernmentService lgaService;

    // Core requirement: given an LGA, return every ward under it
    public List<Ward> findByLocalGovernment(String lgaId) {
        lgaService.findById(lgaId); // 404s early if the LGA doesn't exist
        return wardRepository.findByLocalGovernmentIdOrderByNameAsc(lgaId);
    }

    public Ward findById(String wardId) {
        return wardRepository.findById(wardId)
                .orElseThrow(() -> new ResourceNotFoundException("Ward not found with id: " + wardId));
    }

    @Transactional
    public Ward create(String lgaId, WardRequest request) {
        LocalGovernment lga = lgaService.findById(lgaId);

        if (wardRepository.existsByNameIgnoreCaseAndLocalGovernmentId(request.getName(), lgaId)) {
            throw new DuplicateResourceException(
                    "Ward '" + request.getName() + "' already exists under " + lga.getName());
        }

        Ward ward = Ward.builder().name(request.getName()).build();
        lga.addWard(ward);
        return wardRepository.save(ward);
    }

    @Transactional
    public Ward update(String wardId, WardRequest request) {
        Ward ward = findById(wardId);
        String lgaId = ward.getLocalGovernment().getId();

        if (!ward.getName().equalsIgnoreCase(request.getName())
                && wardRepository.existsByNameIgnoreCaseAndLocalGovernmentId(request.getName(), lgaId)) {
            throw new DuplicateResourceException("Ward name already exists in this LGA");
        }

        ward.setName(request.getName());
        return wardRepository.save(ward);
    }

    @Transactional
    public void delete(String wardId) {
        Ward ward = findById(wardId);
        wardRepository.delete(ward);
    }
}