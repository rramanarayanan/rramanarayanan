package com.abctelecom.paymentservice.repository;

import com.abctelecom.paymentservice.entity.Payment;
import com.abctelecom.paymentservice.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByInvoiceIdOrderByCreatedAtDesc(Long invoiceId);
    List<Payment> findByStatus(PaymentStatus status);
    Optional<Payment> findByPaymentIdAndInvoiceId(Long paymentId, Long invoiceId);
}
