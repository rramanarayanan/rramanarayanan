package com.abctelecom.billingservice.controller;

import com.abctelecom.billingservice.dto.InvoiceRequest;
import com.abctelecom.billingservice.dto.InvoiceResponse;
import com.abctelecom.billingservice.entity.InvoiceStatus;
import com.abctelecom.billingservice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceResponse> createInvoice(@RequestBody InvoiceRequest request) {
        log.info("Creating invoice for customerId: {}", request.getCustomerId());
        InvoiceResponse response = invoiceService.createInvoice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable Long id) {
        log.info("Fetching invoice with id: {}", id);
        InvoiceResponse response = invoiceService.getInvoice(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<InvoiceResponse>> getCustomerInvoices(@PathVariable Long customerId) {
        log.info("Fetching invoices for customerId: {}", customerId);
        List<InvoiceResponse> invoices = invoiceService.getCustomerInvoices(customerId);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<InvoiceResponse>> getInvoicesByStatus(@PathVariable InvoiceStatus status) {
        log.info("Fetching invoices with status: {}", status);
        List<InvoiceResponse> invoices = invoiceService.getInvoicesByStatus(status);
        return ResponseEntity.ok(invoices);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<InvoiceResponse> updateInvoiceStatus(
            @PathVariable Long id,
            @RequestParam InvoiceStatus status) {
        log.info("Updating invoice {} status to {}", id, status);
        InvoiceResponse response = invoiceService.updateInvoiceStatus(id, status);
        return ResponseEntity.ok(response);
    }
}
