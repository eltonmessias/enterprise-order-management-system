package com.eltonmessias.orderservice.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByTenantId(UUID tenantId);
    Optional<Order> findByIdAndTenantId(UUID id, UUID tenantId);

}
