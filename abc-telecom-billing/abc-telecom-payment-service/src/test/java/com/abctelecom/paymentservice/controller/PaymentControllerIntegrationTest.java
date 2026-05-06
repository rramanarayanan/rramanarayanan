package com.abctelecom.paymentservice.controller;

import com.abctelecom.paymentservice.dto.PaymentRequest;
import com.abctelecom.paymentservice.dto.PaymentResponse;
import com.abctelecom.paymentservice.entity.PaymentMethod;
import com.abctelecom.paymentservice.entity.PaymentStatus;
import com.abctelecom.paymentservice.service.PaymentService;
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
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Payment Controller Integration Tests")
class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    private PaymentRequest paymentRequest;
    private PaymentResponse paymentResponse;

    @BeforeEach
    void setUp() {
        paymentRequest = new PaymentRequest();
        paymentRequest.setInvoiceId(1L);
        paymentRequest.setAmount(new BigDecimal("1000.00"));
        paymentRequest.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        paymentResponse = PaymentResponse.builder()
                .paymentId(1L)
                .invoiceId(1L)
                .amount(new BigDecimal("1000.00"))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .status(PaymentStatus.SUCCESS)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should process payment successfully")
    void testProcessPayment() throws Exception {
        // Arrange
        when(paymentService.processPayment(any(PaymentRequest.class))).thenReturn(paymentResponse);

        // Act & Assert
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.paymentId").value(1))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    @DisplayName("Should get payment successfully")
    void testGetPayment() throws Exception {
        // Arrange
        when(paymentService.getPayment(1L)).thenReturn(paymentResponse);

        // Act & Assert
        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(1));
    }

    @Test
    @DisplayName("Should get invoice payments successfully")
    void testGetInvoicePayments() throws Exception {
        // Arrange
        when(paymentService.getInvoicePayments(1L))
                .thenReturn(java.util.Arrays.asList(paymentResponse));

        // Act & Assert
        mockMvc.perform(get("/api/payments/invoice/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].paymentId").value(1));
    }

    @Test
    @DisplayName("Should get payments by status successfully")
    void testGetPaymentsByStatus() throws Exception {
        // Arrange
        when(paymentService.getPaymentsByStatus(PaymentStatus.SUCCESS))
                .thenReturn(java.util.Arrays.asList(paymentResponse));

        // Act & Assert
        mockMvc.perform(get("/api/payments/status/SUCCESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("SUCCESS"));
    }
}
