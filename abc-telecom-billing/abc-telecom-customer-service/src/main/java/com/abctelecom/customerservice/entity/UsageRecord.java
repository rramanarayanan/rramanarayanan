package com.abctelecom.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "usage_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsageRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usageId;

    @Column(nullable = false)
    private Long serviceId;

    @Column(nullable = false)
    private LocalDate usageDate;

    @Column(nullable = false)
    private BigDecimal usageAmount;

    @Column(nullable = false)
    private String unit; // e.g., "MB", "Minutes", "SMS"

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
