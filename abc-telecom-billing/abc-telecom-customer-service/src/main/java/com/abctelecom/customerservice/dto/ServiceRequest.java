package com.abctelecom.customerservice.dto;

import com.abctelecom.customerservice.entity.ServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {
    private String serviceType;
    private LocalDate startDate;
    private ServiceStatus status;
}
