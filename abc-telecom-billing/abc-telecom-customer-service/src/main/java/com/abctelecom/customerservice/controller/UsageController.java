package com.abctelecom.customerservice.controller;

import com.abctelecom.customerservice.dto.UsageRecordDto;
import com.abctelecom.customerservice.service.UsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/services/{serviceId}/usage")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class UsageController {

    private final UsageService usageService;

    @PostMapping
    public ResponseEntity<Object> recordUsage(
            @PathVariable Long serviceId,
            @RequestBody UsageRecordDto dto) {
        log.info("Recording usage for serviceId: {}", serviceId);
        usageService.recordUsage(serviceId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usage recorded successfully");
    }

    @GetMapping
    public ResponseEntity<List<UsageRecordDto>> getUsageHistory(@PathVariable Long serviceId) {
        log.info("Fetching usage history for serviceId: {}", serviceId);
        List<UsageRecordDto> records = usageService.getUsageHistory(serviceId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/range")
    public ResponseEntity<List<UsageRecordDto>> getUsageByDateRange(
            @PathVariable Long serviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("Fetching usage for serviceId: {} from {} to {}", serviceId, startDate, endDate);
        List<UsageRecordDto> records = usageService.getUsageByDateRange(serviceId, startDate, endDate);
        return ResponseEntity.ok(records);
    }
}
