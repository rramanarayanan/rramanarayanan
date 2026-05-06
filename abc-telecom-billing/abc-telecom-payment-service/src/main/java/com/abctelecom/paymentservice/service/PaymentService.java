package com.abctelecom.paymentservice.service;

import com.abctelecom.paymentservice.dto.PaymentRequest;
import com.abctelecom.paymentservice.dto.PaymentResponse;
import com.abctelecom.paymentservice.entity.Payment;
import com.abctelecom.paymentservice.entity.PaymentStatus;
import com.abctelecom.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Processing payment for invoiceId: {}", request.getInvoiceId());

        // Simulate payment processing
        PaymentStatus status = validatePayment(request) ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;

        Payment payment = Payment.builder()
                .invoiceId(request.getInvoiceId())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .status(status)
                .build();

        Payment saved = paymentRepository.save(payment);
        log.info("Payment processed with id: {}", saved.getPaymentId());

        return mapToResponse(saved);
    }

    public PaymentResponse getPayment(Long paymentId) {
        log.info("Fetching payment with id: {}", paymentId);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));
        return mapToResponse(payment);
    }

    public List<PaymentResponse> getInvoicePayments(Long invoiceId) {
        log.info("Fetching payments for invoiceId: {}", invoiceId);
        List<Payment> payments = paymentRepository.findByInvoiceIdOrderByCreatedAtDesc(invoiceId);
        return payments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PaymentResponse> getPaymentsByStatus(PaymentStatus status) {
        log.info("Fetching payments with status: {}", status);
        List<Payment> payments = paymentRepository.findByStatus(status);
        return payments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private boolean validatePayment(PaymentRequest request) {
        // Basic validation logic
        return request.getAmount() != null && request.getAmount().compareTo(java.math.BigDecimal.ZERO) > 0
                && request.getInvoiceId() != null;
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .invoiceId(payment.getInvoiceId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
