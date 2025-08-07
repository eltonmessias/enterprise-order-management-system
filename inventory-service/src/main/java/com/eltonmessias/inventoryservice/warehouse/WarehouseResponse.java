package com.eltonmessias.inventoryservice.warehouse;

import com.eltonmessias.inventoryservice.inventory.Inventory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record WarehouseResponse(
        UUID id,
        UUID tenantId,
        String code,
        String name,
        String address_line1,
        String address_line2,
        String city,
        String state,
        String postalCode,
        String country,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<Inventory> inventories
) {

}
