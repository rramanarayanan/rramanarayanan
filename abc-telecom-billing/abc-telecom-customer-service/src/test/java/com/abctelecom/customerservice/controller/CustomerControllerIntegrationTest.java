package com.abctelecom.customerservice.controller;

import com.abctelecom.customerservice.dto.CustomerRequest;
import com.abctelecom.customerservice.dto.CustomerResponse;
import com.abctelecom.customerservice.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Customer Controller Integration Tests")
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    private CustomerRequest customerRequest;
    private CustomerResponse customerResponse;

    @BeforeEach
    void setUp() {
        customerRequest = new CustomerRequest();
        customerRequest.setUserId(1L);
        customerRequest.setFullName("John Doe");
        customerRequest.setAddress("123 Main St");
        customerRequest.setPhoneNumber("9876543210");

        customerResponse = CustomerResponse.builder()
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
    void testCreateCustomer() throws Exception {
        // Arrange
        when(customerService.createCustomer(any(CustomerRequest.class))).thenReturn(customerResponse);

        // Act & Assert
        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.fullName").value("John Doe"));
    }

    @Test
    @DisplayName("Should get customer successfully")
    void testGetCustomer() throws Exception {
        // Arrange
        when(customerService.getCustomer(1L)).thenReturn(customerResponse);

        // Act & Assert
        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.fullName").value("John Doe"));
    }

    @Test
    @DisplayName("Should update customer successfully")
    void testUpdateCustomer() throws Exception {
        // Arrange
        when(customerService.updateCustomer(eq(1L), any(CustomerRequest.class))).thenReturn(customerResponse);

        // Act & Assert
        mockMvc.perform(put("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1));
    }
}
