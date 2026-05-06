package com.abctelecom.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsageRecordDto {
    private Long usageId;
    private LocalDate usageDate;
    private BigDecimal usageAmount;
    private String unit;
}
