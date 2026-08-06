package com.automation.core.abiaid.service.AbiaStateIdentificationServiceImpl;

import com.automation.core.abiaid.dto.request.AbiaStateIdentificationRequest;
import com.automation.core.abiaid.dto.response.AbiaStateIdentificationResponse;
import com.automation.core.abiaid.model.AbiaStateIdentification;
import com.automation.core.abiaid.repository.AbiaStateIdentificationRepository;
import com.automation.core.abiaid.service.AbiaStateIdentificationService.AbiaStateIdentificationService;
import com.automation.core.global.exception.ResourceNotFoundException;
import com.automation.core.global.model.User;
import com.automation.core.global.repository.UserRepository;
import com.automation.core.lga.model.LocalGovernment;
import com.automation.core.lga.model.Ward;
import com.automation.core.lga.service.LocalGovernmentService;
import com.automation.core.lga.service.WardService;
import com.automation.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbiaStateIdentificationServiceImpl implements AbiaStateIdentificationService {

    private static final String DEFAULT_STATE_OF_ORIGIN = "Abia State";

    private final AbiaStateIdentificationRepository repository;
    private final UserRepository userRepository;
    private final LocalGovernmentService localGovernmentService;
    private final WardService wardService;

    @Override
    public AbiaStateIdentificationResponse apply(AbiaStateIdentificationRequest request) {
        User currentUser = getCurrentUser();
        LocalGovernment lga = localGovernmentService.findById(request.getLgaId());
        Ward ward = wardService.findById(request.getWardId());

        if (ward.getLocalGovernment() == null || !ward.getLocalGovernment().getId().equals(lga.getId())) {
            throw new ResourceNotFoundException("Selected ward does not belong to selected local government");
        }

        AbiaStateIdentification record = AbiaStateIdentification.builder()
                .abiaIdNumber(generateIdNumber())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .email(currentUser.getEmail())
                .phone(currentUser.getPhoneNumber())
                .address(request.getAddress())
                .stateOfOrigin(DEFAULT_STATE_OF_ORIGIN)
                .lga(lga.getName())
                .ward(ward.getName())
                .nin(request.getNin())
                .dateOfBirth(request.getDateOfBirth())
                .status(Status.PENDING)
                .dateApplied(LocalDate.now())
                .createdBy(currentUser)
                .build();

        AbiaStateIdentification saved = repository.save(record);
        return toResponse(saved);
    }

    @Override
    public List<AbiaStateIdentificationResponse> getMyApplications() {
        return repository.findByCreatedByOrderByIdDesc(getCurrentUser())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private AbiaStateIdentificationResponse toResponse(AbiaStateIdentification entity) {
        return AbiaStateIdentificationResponse.builder()
                .id(entity.getId())
                .abiaIdNumber(entity.getAbiaIdNumber())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .stateOfOrigin(entity.getStateOfOrigin())
                .lga(entity.getLga())
                .ward(entity.getWard())
                .nin(entity.getNin())
                .dateOfBirth(entity.getDateOfBirth())
                .status(entity.getStatus())
                .dateApplied(entity.getDateApplied())
                .build();
    }

    private String generateIdNumber() {
        return "ABIA-ID-" + System.currentTimeMillis() + "-" + (1000 + new Random().nextInt(9000));
    }

    private User getCurrentUser() {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
