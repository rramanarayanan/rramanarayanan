package com.abctelecom.paymentservice.dto;

import com.abctelecom.paymentservice.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long invoiceId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
}
