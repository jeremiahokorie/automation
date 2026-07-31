package com.automation.core.lga.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class LocalGovernmentResponse {
    private String id;
    private String name;
    private String headquarters;
    private int wardCount;
    private List<WardResponse> wards;
}
