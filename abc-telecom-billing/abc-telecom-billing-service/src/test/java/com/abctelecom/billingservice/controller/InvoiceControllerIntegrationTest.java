package com.abctelecom.billingservice.controller;

import com.abctelecom.billingservice.dto.InvoiceRequest;
import com.abctelecom.billingservice.dto.InvoiceResponse;
import com.abctelecom.billingservice.entity.InvoiceStatus;
import com.abctelecom.billingservice.service.InvoiceService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Invoice Controller Integration Tests")
class InvoiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InvoiceService invoiceService;

    private InvoiceRequest invoiceRequest;
    private InvoiceResponse invoiceResponse;

    @BeforeEach
    void setUp() {
        invoiceRequest = new InvoiceRequest();
        invoiceRequest.setCustomerId(1L);
        invoiceRequest.setBillingPeriodStart(LocalDate.of(2024, 1, 1));
        invoiceRequest.setBillingPeriodEnd(LocalDate.of(2024, 1, 31));
        invoiceRequest.setTotalAmount(new BigDecimal("1000.00"));

        invoiceResponse = InvoiceResponse.builder()
                .invoiceId(1L)
                .customerId(1L)
                .billingPeriodStart(LocalDate.of(2024, 1, 1))
                .billingPeriodEnd(LocalDate.of(2024, 1, 31))
                .totalAmount(new BigDecimal("1000.00"))
                .status(InvoiceStatus.UNPAID)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should create invoice successfully")
    void testCreateInvoice() throws Exception {
        // Arrange
        when(invoiceService.createInvoice(any(InvoiceRequest.class))).thenReturn(invoiceResponse);

        // Act & Assert
        mockMvc.perform(post("/api/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invoiceRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.invoiceId").value(1))
                .andExpect(jsonPath("$.status").value("UNPAID"));
    }

    @Test
    @DisplayName("Should get invoice successfully")
    void testGetInvoice() throws Exception {
        // Arrange
        when(invoiceService.getInvoice(1L)).thenReturn(invoiceResponse);

        // Act & Assert
        mockMvc.perform(get("/api/invoices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceId").value(1));
    }

    @Test
    @DisplayName("Should get customer invoices successfully")
    void testGetCustomerInvoices() throws Exception {
        // Arrange
        when(invoiceService.getCustomerInvoices(1L))
                .thenReturn(java.util.Arrays.asList(invoiceResponse));

        // Act & Assert
        mockMvc.perform(get("/api/invoices/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].invoiceId").value(1));
    }

    @Test
    @DisplayName("Should update invoice status successfully")
    void testUpdateInvoiceStatus() throws Exception {
        // Arrange
        invoiceResponse.setStatus(InvoiceStatus.PAID);
        when(invoiceService.updateInvoiceStatus(1L, InvoiceStatus.PAID))
                .thenReturn(invoiceResponse);

        // Act & Assert
        mockMvc.perform(patch("/api/invoices/1/status")
                .param("status", "PAID"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"));
    }
}
