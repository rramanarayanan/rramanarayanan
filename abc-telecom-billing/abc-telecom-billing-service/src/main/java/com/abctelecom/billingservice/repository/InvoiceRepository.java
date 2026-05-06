package com.abctelecom.billingservice.repository;

import com.abctelecom.billingservice.entity.Invoice;
import com.abctelecom.billingservice.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByCustomerId(Long customerId);
    List<Invoice> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
    List<Invoice> findByStatus(InvoiceStatus status);
    Optional<Invoice> findByInvoiceIdAndCustomerId(Long invoiceId, Long customerId);
}
