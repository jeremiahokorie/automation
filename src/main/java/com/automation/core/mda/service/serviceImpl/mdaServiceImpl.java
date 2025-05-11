package com.automation.core.mda.service.serviceImpl;

import com.automation.core.global.exception.CustomException;
import com.automation.core.mda.dto.request.mdaRequest;
import com.automation.core.mda.dto.response.mdaResponse;
import com.automation.core.mda.model.ServicesModel;
import com.automation.core.mda.model.mdaModel;
import com.automation.core.mda.repository.ServiceRepository;
import com.automation.core.mda.repository.mdaRepository;
import com.automation.core.mda.service.service.mdaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class mdaServiceImpl implements mdaService {

    private final mdaRepository repository;
    private final ServiceRepository serviceRepository;

    @Override
    public mdaResponse createMdaWithServices(String mdaName, List<String> services) {
        mdaModel mda = new mdaModel();
        mda.setName(mdaName);
        List<ServicesModel> mdanameservice = services.stream()
                .map(name -> {
                    ServicesModel service = new ServicesModel();
                    service.setName(name);
                    service.setMda(mda);
                    return service;
                }).collect(Collectors.toList());
        mda.setServices(mdanameservice);
        repository.save(mda);
        return mdaResponse.builder().code(mda.getCode()).name(mda.getName()).build();
    }

    @Override
    public mdaResponse createMda(mdaRequest mdaRequest) {
        Optional<mdaModel> mda = repository.findBycode(mdaRequest.getCode());
        if (mda.isPresent()) {
            throw new CustomException("MDA already exists");
        }

        mdaModel mdaModel = new mdaModel();
        mdaModel.setCode(mdaRequest.getCode());
        mdaModel.setName(mdaRequest.getName());
        return mdaResponse.builder().name(mdaRequest.getName()).code(mdaRequest.getCode()).build();
    }

    @Override
    public List<mdaResponse> getMdas() {
        List<mdaModel> mdas = repository.findAll();
        return mdas.stream().map(mda -> mdaResponse.builder()
                .code(mda.getCode())
                .name(mda.getName())
                .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteMdaByCode(String mdaCode) {
        if (!repository.existsByCode(mdaCode)) {
            throw new EntityNotFoundException("MDA with code " + mdaCode + " not found");
        }
        repository.deleteByCode(mdaCode);
    }

    @Override
    public List<ServicesModel> getServicesByMda(Long mdaId) {
        return serviceRepository.findByMdaId(mdaId);
    }

}
