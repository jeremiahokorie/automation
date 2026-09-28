package com.automation.core.reporting.service;

import com.automation.core.reporting.enums.ExportFormat;
import com.automation.core.reporting.enums.ReportSource;
import com.automation.core.reporting.model.ReportResponse;
import com.automation.core.reporting.provider.ReportProvider;
import com.automation.core.reporting.dto.request.ReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportingDispatcher {
    private final Map<ReportSource, ReportProvider> providers = new EnumMap<>(ReportSource.class);

    public ReportingDispatcher(List<ReportProvider> providerList) {
        providerList.forEach(p -> providers.put(p.getSource(), p));
    }

    public ReportResponse dispatch(ReportRequest request) {
        ReportProvider provider = providers.get(request.getSource());
        if (provider == null) {
            throw new UnsupportedOperationException("Report source not supported: " + request.getSource());
        }
        return provider.generateReport(request);
    }
}
