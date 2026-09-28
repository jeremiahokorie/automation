package com.automation.core.tracking.service;

import com.automation.core.global.model.User;
import com.automation.core.tracking.dto.response.TrackingApplicationResponse;
import com.automation.core.tracking.enums.ApplicationStatus;
import com.automation.core.tracking.model.ApplicationTracking;
import com.automation.core.tracking.repository.ApplicationTrackingRepository;
import com.automation.core.mda.service.service.mdaService;
import com.automation.core.mda.model.ServicesModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationTrackingServiceImplTest {

    @Mock
    private ApplicationTrackingRepository trackingRepository;

    @Mock
    private com.automation.core.mda.service.service.mdaService mdaService;

    @InjectMocks
    private ApplicationTrackingServiceImpl trackingService;

    private User user;
    private ApplicationTracking tracking;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        tracking = new ApplicationTracking();
        tracking.setTrackingReference("TRK-12345");
        tracking.setServiceId("Environment Permit");
        tracking.setUser(user);
        tracking.setCurrentStatus(ApplicationStatus.PENDING);
    }

    @Test
    void findMyApplications_WithSearch_ShouldFilterByReference() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ApplicationTracking> page = new PageImpl<>(Collections.singletonList(tracking));

        // Mock the repository call - Cast to JpaSpecificationExecutor to resolve ambiguity
        when(((org.springframework.data.jpa.repository.JpaSpecificationExecutor<ApplicationTracking>) trackingRepository).findAll(any(), eq(pageable))).thenReturn(page);

        Page<TrackingApplicationResponse> result = trackingService.findMyApplications(user, "TRK-12345", null, null, null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("TRK-12345", result.getContent().get(0).getTrackingReference());
    }

    @Test
    void findMyApplications_WithMdaId_ShouldFilterByMdaServices() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ApplicationTracking> page = new PageImpl<>(Collections.singletonList(tracking));

        Long mdaId = 10L;
        ServicesModel service = new ServicesModel();
        service.setName("Environment Permit");

        List<ServicesModel> services = Collections.singletonList(service);
        when(mdaService.getServicesByMda(mdaId)).thenReturn(services);
        when(((org.springframework.data.jpa.repository.JpaSpecificationExecutor<ApplicationTracking>) trackingRepository).findAll(any(), eq(pageable))).thenReturn(page);

        Page<TrackingApplicationResponse> result = trackingService.findMyApplications(user, null, null, mdaId.toString(), null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(mdaService).getServicesByMda(mdaId);
    }

    @Test
    void findMyApplications_WithServiceId_ShouldFilterByService() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ApplicationTracking> page = new PageImpl<>(Collections.singletonList(tracking));

        when(((org.springframework.data.jpa.repository.JpaSpecificationExecutor<ApplicationTracking>) trackingRepository).findAll(any(), eq(pageable))).thenReturn(page);

        Page<TrackingApplicationResponse> result = trackingService.findMyApplications(user, null, null, null, "Environment Permit", pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void findMyApplications_WithStatus_ShouldFilterByStatus() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ApplicationTracking> page = new PageImpl<>(Collections.singletonList(tracking));

        when(((org.springframework.data.jpa.repository.JpaSpecificationExecutor<ApplicationTracking>) trackingRepository).findAll(any(), eq(pageable))).thenReturn(page);

        Page<TrackingApplicationResponse> result = trackingService.findMyApplications(user, null, ApplicationStatus.PENDING, null, null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }
}
