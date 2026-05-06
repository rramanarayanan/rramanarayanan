package com.abctelecom.billingservice.service;

import com.abctelecom.billingservice.dto.InvoiceRequest;
import com.abctelecom.billingservice.dto.InvoiceResponse;
import com.abctelecom.billingservice.entity.Invoice;
import com.abctelecom.billingservice.entity.InvoiceStatus;
import com.abctelecom.billingservice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        log.info("Creating invoice for customerId: {}", request.getCustomerId());

        Invoice invoice = Invoice.builder()
                .customerId(request.getCustomerId())
                .billingPeriodStart(request.getBillingPeriodStart())
                .billingPeriodEnd(request.getBillingPeriodEnd())
                .totalAmount(request.getTotalAmount())
                .status(InvoiceStatus.UNPAID)
                .build();

        Invoice saved = invoiceRepository.save(invoice);
        log.info("Invoice created with id: {}", saved.getInvoiceId());

        return mapToResponse(saved);
    }

    public InvoiceResponse getInvoice(Long invoiceId) {
        log.info("Fetching invoice with id: {}", invoiceId);
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + invoiceId));
        return mapToResponse(invoice);
    }

    public List<InvoiceResponse> getCustomerInvoices(Long customerId) {
        log.info("Fetching invoices for customerId: {}", customerId);
        List<Invoice> invoices = invoiceRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
        return invoices.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<InvoiceResponse> getInvoicesByStatus(InvoiceStatus status) {
        log.info("Fetching invoices with status: {}", status);
        List<Invoice> invoices = invoiceRepository.findByStatus(status);
        return invoices.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public InvoiceResponse updateInvoiceStatus(Long invoiceId, InvoiceStatus status) {
        log.info("Updating invoice {} status to {}", invoiceId, status);
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + invoiceId));

        invoice.setStatus(status);
        Invoice updated = invoiceRepository.save(invoice);
        return mapToResponse(updated);
    }

    private InvoiceResponse mapToResponse(Invoice invoice) {
        return InvoiceResponse.builder()
                .invoiceId(invoice.getInvoiceId())
                .customerId(invoice.getCustomerId())
                .billingPeriodStart(invoice.getBillingPeriodStart())
                .billingPeriodEnd(invoice.getBillingPeriodEnd())
                .totalAmount(invoice.getTotalAmount())
                .status(invoice.getStatus())
                .createdAt(invoice.getCreatedAt())
                .updatedAt(invoice.getUpdatedAt())
                .build();
    }
}
