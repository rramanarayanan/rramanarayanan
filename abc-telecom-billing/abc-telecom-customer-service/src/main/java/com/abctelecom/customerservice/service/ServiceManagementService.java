package com.abctelecom.customerservice.service;

import com.abctelecom.customerservice.dto.ServiceRequest;
import com.abctelecom.customerservice.entity.Service;
import com.abctelecom.customerservice.entity.ServiceStatus;
import com.abctelecom.customerservice.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceManagementService {

    private final ServiceRepository serviceRepository;

    @Transactional
    public Service createService(Long customerId, ServiceRequest request) {
        log.info("Creating service for customerId: {}", customerId);

        com.abctelecom.customerservice.entity.Service service = com.abctelecom.customerservice.entity.Service.builder()
                .customerId(customerId)
                .serviceType(request.getServiceType())
                .startDate(request.getStartDate())
                .status(request.getStatus() != null ? request.getStatus() : ServiceStatus.ACTIVE)
                .build();

        Service saved = serviceRepository.save(service);
        log.info("Service created with id: {}", saved.getServiceId());

        return saved;
    }

    public List<Service> getCustomerServices(Long customerId) {
        log.info("Fetching services for customerId: {}", customerId);
        return serviceRepository.findByCustomerId(customerId);
    }

    public Service getService(Long serviceId, Long customerId) {
        log.info("Fetching service with id: {} for customerId: {}", serviceId, customerId);
        return serviceRepository.findByServiceIdAndCustomerId(serviceId, customerId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }

    @Transactional
    public Service updateService(Long serviceId, Long customerId, ServiceRequest request) {
        log.info("Updating service with id: {} for customerId: {}", serviceId, customerId);
        com.abctelecom.customerservice.entity.Service service = serviceRepository.findByServiceIdAndCustomerId(serviceId, customerId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        service.setServiceType(request.getServiceType());
        service.setStatus(request.getStatus());

        return serviceRepository.save(service);
    }
}
