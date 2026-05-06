package com.abctelecom.paymentservice.service;

import com.abctelecom.paymentservice.dto.PaymentRequest;
import com.abctelecom.paymentservice.dto.PaymentResponse;
import com.abctelecom.paymentservice.entity.Payment;
import com.abctelecom.paymentservice.entity.PaymentMethod;
import com.abctelecom.paymentservice.entity.PaymentStatus;
import com.abctelecom.paymentservice.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Payment Service Unit Tests")
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private PaymentRequest paymentRequest;
    private Payment testPayment;

    @BeforeEach
    void setUp() {
        paymentRequest = new PaymentRequest();
        paymentRequest.setInvoiceId(1L);
        paymentRequest.setAmount(new BigDecimal("1000.00"));
        paymentRequest.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        testPayment = Payment.builder()
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
    void testProcessPaymentSuccess() {
        // Arrange
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        // Act
        PaymentResponse response = paymentService.processPayment(paymentRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getPaymentId());
        assertEquals(PaymentStatus.SUCCESS, response.getStatus());
        assertEquals(new BigDecimal("1000.00"), response.getAmount());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    @DisplayName("Should fail payment with invalid amount")
    void testProcessPaymentInvalidAmount() {
        // Arrange
        paymentRequest.setAmount(BigDecimal.ZERO);
        testPayment.setStatus(PaymentStatus.FAILED);
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        // Act
        PaymentResponse response = paymentService.processPayment(paymentRequest);

        // Assert
        assertEquals(PaymentStatus.FAILED, response.getStatus());
    }

    @Test
    @DisplayName("Should get payment successfully")
    void testGetPaymentSuccess() {
        // Arrange
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));

        // Act
        PaymentResponse response = paymentService.getPayment(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getPaymentId());
    }

    @Test
    @DisplayName("Should throw exception when payment not found")
    void testGetPaymentNotFound() {
        // Arrange
        when(paymentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> paymentService.getPayment(999L));
    }

    @Test
    @DisplayName("Should get invoice payments successfully")
    void testGetInvoicePaymentsSuccess() {
        // Arrange
        List<Payment> payments = Arrays.asList(testPayment);
        when(paymentRepository.findByInvoiceIdOrderByCreatedAtDesc(1L)).thenReturn(payments);

        // Act
        List<PaymentResponse> responses = paymentService.getInvoicePayments(1L);

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getPaymentId());
    }

    @Test
    @DisplayName("Should get payments by status")
    void testGetPaymentsByStatus() {
        // Arrange
        List<Payment> payments = Arrays.asList(testPayment);
        when(paymentRepository.findByStatus(PaymentStatus.SUCCESS)).thenReturn(payments);

        // Act
        List<PaymentResponse> responses = paymentService.getPaymentsByStatus(PaymentStatus.SUCCESS);

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(PaymentStatus.SUCCESS, responses.get(0).getStatus());
    }
}
