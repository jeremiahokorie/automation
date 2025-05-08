package com.automation.core.mda.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class mdaRequest {
    private Long id;
    private String name;
    private String code;
}
