package com.abctelecom.customerservice.service;

import com.abctelecom.customerservice.dto.CustomerRequest;
import com.abctelecom.customerservice.dto.CustomerResponse;
import com.abctelecom.customerservice.entity.Customer;
import com.abctelecom.customerservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Service Unit Tests")
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private CustomerRequest customerRequest;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        customerRequest = new CustomerRequest();
        customerRequest.setUserId(1L);
        customerRequest.setFullName("John Doe");
        customerRequest.setAddress("123 Main St");
        customerRequest.setPhoneNumber("9876543210");

        testCustomer = Customer.builder()
                .customerId(1L)
                .userId(1L)
                .fullName("John Doe")
                .address("123 Main St")
                .phoneNumber("9876543210")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should create customer successfully")
    void testCreateCustomerSuccess() {
        // Arrange
        when(customerRepository.existsByUserId(1L)).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // Act
        CustomerResponse response = customerService.createCustomer(customerRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getCustomerId());
        assertEquals("John Doe", response.getFullName());
        assertEquals("123 Main St", response.getAddress());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should fail to create customer when user already has customer record")
    void testCreateCustomerDuplicate() {
        // Arrange
        when(customerRepository.existsByUserId(1L)).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> customerService.createCustomer(customerRequest));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should get customer successfully")
    void testGetCustomerSuccess() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        // Act
        CustomerResponse response = customerService.getCustomer(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getCustomerId());
        assertEquals("John Doe", response.getFullName());
    }

    @Test
    @DisplayName("Should throw exception when customer not found")
    void testGetCustomerNotFound() {
        // Arrange
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> customerService.getCustomer(999L));
    }

    @Test
    @DisplayName("Should update customer successfully")
    void testUpdateCustomerSuccess() {
        // Arrange
        CustomerRequest updateRequest = new CustomerRequest();
        updateRequest.setFullName("Jane Doe");
        updateRequest.setAddress("456 Oak St");
        updateRequest.setPhoneNumber("9999999999");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // Act
        CustomerResponse response = customerService.updateCustomer(1L, updateRequest);

        // Assert
        assertNotNull(response);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}
