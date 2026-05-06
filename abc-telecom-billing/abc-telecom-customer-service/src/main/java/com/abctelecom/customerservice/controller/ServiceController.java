package com.abctelecom.customerservice.controller;

import com.abctelecom.customerservice.dto.ServiceRequest;
import com.abctelecom.customerservice.entity.Service;
import com.abctelecom.customerservice.service.ServiceManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/services")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class ServiceController {

    private final ServiceManagementService serviceManagementService;

    @PostMapping
    public ResponseEntity<Service> createService(
            @PathVariable Long customerId,
            @RequestBody ServiceRequest request) {
        log.info("Creating service for customerId: {}", customerId);
        Service service = serviceManagementService.createService(customerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service);
    }

    @GetMapping
    public ResponseEntity<List<Service>> getCustomerServices(@PathVariable Long customerId) {
        log.info("Fetching services for customerId: {}", customerId);
        List<Service> services = serviceManagementService.getCustomerServices(customerId);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<Service> getService(
            @PathVariable Long customerId,
            @PathVariable Long serviceId) {
        log.info("Fetching service {} for customerId: {}", serviceId, customerId);
        Service service = serviceManagementService.getService(serviceId, customerId);
        return ResponseEntity.ok(service);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<Service> updateService(
            @PathVariable Long customerId,
            @PathVariable Long serviceId,
            @RequestBody ServiceRequest request) {
        log.info("Updating service {} for customerId: {}", serviceId, customerId);
        Service service = serviceManagementService.updateService(serviceId, customerId, request);
        return ResponseEntity.ok(service);
    }
}
