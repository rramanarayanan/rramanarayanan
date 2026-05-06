package com.abctelecom.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private String serviceType; // e.g., "Postpaid Mobile", "Landline", "Broadband"

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceStatus status; // ACTIVE, INACTIVE, SUSPENDED

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
