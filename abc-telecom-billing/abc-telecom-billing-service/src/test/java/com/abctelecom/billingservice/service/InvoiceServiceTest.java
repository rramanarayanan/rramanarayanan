package com.abctelecom.billingservice.service;

import com.abctelecom.billingservice.dto.InvoiceRequest;
import com.abctelecom.billingservice.dto.InvoiceResponse;
import com.abctelecom.billingservice.entity.Invoice;
import com.abctelecom.billingservice.entity.InvoiceStatus;
import com.abctelecom.billingservice.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Invoice Service Unit Tests")
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    private InvoiceRequest invoiceRequest;
    private Invoice testInvoice;

    @BeforeEach
    void setUp() {
        invoiceRequest = new InvoiceRequest();
        invoiceRequest.setCustomerId(1L);
        invoiceRequest.setBillingPeriodStart(LocalDate.of(2024, 1, 1));
        invoiceRequest.setBillingPeriodEnd(LocalDate.of(2024, 1, 31));
        invoiceRequest.setTotalAmount(new BigDecimal("1000.00"));

        testInvoice = Invoice.builder()
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
    void testCreateInvoiceSuccess() {
        // Arrange
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(testInvoice);

        // Act
        InvoiceResponse response = invoiceService.createInvoice(invoiceRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getInvoiceId());
        assertEquals(InvoiceStatus.UNPAID, response.getStatus());
        assertEquals(new BigDecimal("1000.00"), response.getTotalAmount());
        verify(invoiceRepository, times(1)).save(any(Invoice.class));
    }

    @Test
    @DisplayName("Should get invoice successfully")
    void testGetInvoiceSuccess() {
        // Arrange
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(testInvoice));

        // Act
        InvoiceResponse response = invoiceService.getInvoice(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getInvoiceId());
    }

    @Test
    @DisplayName("Should throw exception when invoice not found")
    void testGetInvoiceNotFound() {
        // Arrange
        when(invoiceRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> invoiceService.getInvoice(999L));
    }

    @Test
    @DisplayName("Should get customer invoices successfully")
    void testGetCustomerInvoicesSuccess() {
        // Arrange
        List<Invoice> invoices = Arrays.asList(testInvoice);
        when(invoiceRepository.findByCustomerIdOrderByCreatedAtDesc(1L)).thenReturn(invoices);

        // Act
        List<InvoiceResponse> responses = invoiceService.getCustomerInvoices(1L);

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getInvoiceId());
    }

    @Test
    @DisplayName("Should update invoice status successfully")
    void testUpdateInvoiceStatusSuccess() {
        // Arrange
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(testInvoice));
        testInvoice.setStatus(InvoiceStatus.PAID);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(testInvoice);

        // Act
        InvoiceResponse response = invoiceService.updateInvoiceStatus(1L, InvoiceStatus.PAID);

        // Assert
        assertNotNull(response);
        assertEquals(InvoiceStatus.PAID, response.getStatus());
    }

    @Test
    @DisplayName("Should get invoices by status")
    void testGetInvoicesByStatus() {
        // Arrange
        List<Invoice> invoices = Arrays.asList(testInvoice);
        when(invoiceRepository.findByStatus(InvoiceStatus.UNPAID)).thenReturn(invoices);

        // Act
        List<InvoiceResponse> responses = invoiceService.getInvoicesByStatus(InvoiceStatus.UNPAID);

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(InvoiceStatus.UNPAID, responses.get(0).getStatus());
    }
}
