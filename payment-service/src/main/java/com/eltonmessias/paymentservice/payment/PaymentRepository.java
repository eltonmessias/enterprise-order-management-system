package com.eltonmessias.paymentservice.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByIdAndTenantId(UUID id, UUID tenantId);
    List<Payment> findAllByTenantId(UUID tenantId);
}
