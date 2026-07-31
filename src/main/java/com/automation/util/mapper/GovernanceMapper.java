package com.automation.util.mapper;

import com.automation.core.lga.dto.LocalGovernmentResponse;
import com.automation.core.lga.dto.WardResponse;
import com.automation.core.lga.model.LocalGovernment;
import com.automation.core.lga.model.Ward;
import java.util.List;


public class GovernanceMapper {
    public static WardResponse toWardResponse(Ward ward) {
        return WardResponse.builder()
                .id(ward.getId())
                .name(ward.getName())
                .localGovernmentId(ward.getLocalGovernment().getId())
                .localGovernmentName(ward.getLocalGovernment().getName())
                .build();
    }

    public static LocalGovernmentResponse toLgaResponse(LocalGovernment lga, boolean includeWards) {
        List<WardResponse> wards = includeWards
                ? lga.getWards().stream().map(GovernanceMapper::toWardResponse).toList()
                : List.of();

        return LocalGovernmentResponse.builder()
                .id(lga.getId())
                .name(lga.getName())
                .headquarters(lga.getHeadquarters())
                .wardCount(lga.getWards().size())
                .wards(wards)
                .build();
    }
}
