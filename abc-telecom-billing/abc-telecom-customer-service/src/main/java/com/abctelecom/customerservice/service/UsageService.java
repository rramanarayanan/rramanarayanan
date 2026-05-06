package com.abctelecom.customerservice.service;

import com.abctelecom.customerservice.dto.UsageRecordDto;
import com.abctelecom.customerservice.entity.UsageRecord;
import com.abctelecom.customerservice.repository.UsageRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsageService {

    private final UsageRecordRepository usageRecordRepository;

    @Transactional
    public UsageRecord recordUsage(Long serviceId, UsageRecordDto dto) {
        log.info("Recording usage for serviceId: {}", serviceId);

        UsageRecord record = UsageRecord.builder()
                .serviceId(serviceId)
                .usageDate(dto.getUsageDate())
                .usageAmount(dto.getUsageAmount())
                .unit(dto.getUnit())
                .build();

        return usageRecordRepository.save(record);
    }

    public List<UsageRecordDto> getUsageHistory(Long serviceId) {
        log.info("Fetching usage history for serviceId: {}", serviceId);
        List<UsageRecord> records = usageRecordRepository.findByServiceIdOrderByUsageDateDesc(serviceId);
        return records.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<UsageRecordDto> getUsageByDateRange(Long serviceId, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching usage for serviceId: {} from {} to {}", serviceId, startDate, endDate);
        List<UsageRecord> records = usageRecordRepository.findByServiceIdAndUsageDateBetween(serviceId, startDate, endDate);
        return records.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private UsageRecordDto mapToDto(UsageRecord record) {
        return UsageRecordDto.builder()
                .usageId(record.getUsageId())
                .usageDate(record.getUsageDate())
                .usageAmount(record.getUsageAmount())
                .unit(record.getUnit())
                .build();
    }
}
