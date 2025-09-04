package com.eltonmessias.inventoryservice.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    Page<Inventory> findAllByTenantId(UUID tenantId);

    Optional<Inventory> findByIdAndTenantId (UUID inventoryId, UUID tenantId);
}
