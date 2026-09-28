package com.automation.core.health.model;

import com.automation.core.global.model.User;
import com.automation.util.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HealthFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String facilityName;
    private String facilityType; // e.g., Clinic, Hospital, Laboratory
    private String address;
    private String phone;
    private String email;
    private String ownerName;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String permitUrl;
    private LocalDateTime permitGeneratedDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdBy;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = Status.PENDING;
        }
    }
}
