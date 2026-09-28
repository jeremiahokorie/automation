package com.automation.core.revenue.service.serviceImpl;

import com.automation.core.mda.model.mdaModel;
import com.automation.core.mda.repository.mdaRepository;
import com.automation.core.revenue.dto.request.MdaRevenueHeadMappingRequest;
import com.automation.core.revenue.dto.request.RevenueHeadRequest;
import com.automation.core.revenue.dto.response.RevenueHeadResponse;
import com.automation.core.revenue.model.RevenueHead;
import com.automation.core.revenue.repository.RevenueHeadRepository;
import com.automation.core.revenue.service.service.RevenueHeadService;
import com.automation.core.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RevenueHeadServiceImpl implements RevenueHeadService {

    private final RevenueHeadRepository revenueHeadRepository;
    private final mdaRepository mdaRepository;

    @Override
    @Transactional
    public RevenueHeadResponse createRevenueHead(RevenueHeadRequest request) {
        RevenueHead revenueHead = RevenueHead.builder()
                .name(request.getName())
                .revenueHeadCode(request.getRevenueHeadCode())
                .amount(request.getAmount())
                .build();

        RevenueHead saved = revenueHeadRepository.save(revenueHead);
        return mapToResponse(saved);
    }

    @Override
    public List<RevenueHeadResponse> getAllRevenueHeads() {
        return revenueHeadRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void mapMdaToRevenueHead(MdaRevenueHeadMappingRequest mappingRequest) {
        RevenueHead revenueHead = revenueHeadRepository.findById(mappingRequest.getRevenueHeadId())
                .orElseThrow(() -> new ResourceNotFoundException("Revenue Head not found"));

        mdaModel mda = mdaRepository.findById(mappingRequest.getMdaId())
                .orElseThrow(() -> new ResourceNotFoundException("MDA not found"));

        if (!revenueHead.getMdas().contains(mda)) {
            revenueHead.getMdas().add(mda);
            revenueHeadRepository.save(revenueHead);
        }
    }

    private RevenueHeadResponse mapToResponse(RevenueHead revenueHead) {
        return RevenueHeadResponse.builder()
                .id(revenueHead.getId())
                .name(revenueHead.getName())
                .revenueHeadCode(revenueHead.getRevenueHeadCode())
                .amount(revenueHead.getAmount())
                .build();
    }
}
