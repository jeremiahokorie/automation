package com.automation.core.lga.dto;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class WardResponse {
    private String id;
    private String name;
    private String localGovernmentId;
    private String localGovernmentName;
}