package com.abctelecom.customerservice.repository;

import com.abctelecom.customerservice.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByCustomerId(Long customerId);
    Optional<Service> findByServiceIdAndCustomerId(Long serviceId, Long customerId);
}
