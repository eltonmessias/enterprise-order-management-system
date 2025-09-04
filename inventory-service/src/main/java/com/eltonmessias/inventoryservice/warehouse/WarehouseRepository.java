package com.eltonmessias.inventoryservice.warehouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
    Optional<Warehouse> findByIdAndTenantId(UUID id, UUID tenantId);
    List<Warehouse> findAllByTenantId(UUID tenantId);
    boolean existsById(UUID id);
}
