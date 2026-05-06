package com.abctelecom.customerservice.service;

import com.abctelecom.customerservice.dto.CustomerRequest;
import com.abctelecom.customerservice.dto.CustomerResponse;
import com.abctelecom.customerservice.entity.Customer;
import com.abctelecom.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        log.info("Creating customer for userId: {}", request.getUserId());

        if (customerRepository.existsByUserId(request.getUserId())) {
            throw new RuntimeException("Customer already exists for userId: " + request.getUserId());
        }

        Customer customer = Customer.builder()
                .userId(request.getUserId())
                .fullName(request.getFullName())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();

        Customer saved = customerRepository.save(customer);
        log.info("Customer created with id: {}", saved.getCustomerId());

        return mapToResponse(saved);
    }

    public CustomerResponse getCustomer(Long customerId) {
        log.info("Fetching customer with id: {}", customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        return mapToResponse(customer);
    }

    @Transactional
    public CustomerResponse updateCustomer(Long customerId, CustomerRequest request) {
        log.info("Updating customer with id: {}", customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        customer.setFullName(request.getFullName());
        customer.setAddress(request.getAddress());
        customer.setPhoneNumber(request.getPhoneNumber());

        Customer updated = customerRepository.save(customer);
        log.info("Customer updated with id: {}", customerId);

        return mapToResponse(updated);
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .userId(customer.getUserId())
                .fullName(customer.getFullName())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}
