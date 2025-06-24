package com.automation.util.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoBipSendSmsMessageRequest {
    private List<InfoBipSendSmsDestinationRequest> destinations;
    @Builder.Default
    private String from = "InfoSMS";
    private String text;
}
