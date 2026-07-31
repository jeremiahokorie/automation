package com.automation.core.lga.service;

import com.automation.core.global.exception.DuplicateResourceException;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.core.lga.dto.LocalGovernmentRequest;
import com.automation.core.lga.model.LocalGovernment;
import com.automation.core.lga.repository.LocalGovernmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocalGovernmentService {
    private final LocalGovernmentRepository lgaRepository;

    public List<LocalGovernment> findAll() {
        return lgaRepository.findAll();
    }

    public LocalGovernment findById(String id) {
        return lgaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Local Government not found with id: " + id));
    }

    @Transactional
    public LocalGovernment create(LocalGovernmentRequest request) {
        if (lgaRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException("LGA already exists: " + request.getName());
        }
        LocalGovernment lga = LocalGovernment.builder()
                .name(request.getName())
                .headquarters(request.getHeadquarters())
                .build();
        return lgaRepository.save(lga);
    }

    @Transactional
    public LocalGovernment update(String id, LocalGovernmentRequest request) {
        LocalGovernment lga = findById(id);

        if (!lga.getName().equalsIgnoreCase(request.getName())
                && lgaRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException("LGA already exists: " + request.getName());
        }

        lga.setName(request.getName());
        lga.setHeadquarters(request.getHeadquarters());
        return lgaRepository.save(lga);
    }

    @Transactional
    public void delete(String id) {
        LocalGovernment lga = findById(id);
        lgaRepository.delete(lga); // cascades to wards
    }
}