package com.abctelecom.customerservice.repository;

import com.abctelecom.customerservice.entity.UsageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UsageRecordRepository extends JpaRepository<UsageRecord, Long> {
    List<UsageRecord> findByServiceIdOrderByUsageDateDesc(Long serviceId);
    List<UsageRecord> findByServiceIdAndUsageDateBetween(Long serviceId, LocalDate startDate, LocalDate endDate);
}
