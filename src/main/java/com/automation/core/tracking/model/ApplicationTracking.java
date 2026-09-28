package com.automation.core.tracking.model;

import com.automation.core.global.model.User;
import com.automation.core.tracking.enums.ActionType;
import com.automation.core.tracking.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_tracking", indexes = {
    @Index(name = "idx_tracking_user", columnList = "user_id"),
    @Index(name = "idx_tracking_ref", columnList = "trackingReference")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String trackingReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String serviceId;

    @Column(nullable = false)
    private Long applicationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus currentStatus;

    @Column(nullable = false)
    private String currentStage;

    @Builder.Default
    private boolean isActionRequired = false;

    private String actionMessage;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
