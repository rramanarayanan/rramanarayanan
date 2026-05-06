package com.abctelecom.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private Long userId;
    private String fullName;
    private String address;
    private String phoneNumber;
}
