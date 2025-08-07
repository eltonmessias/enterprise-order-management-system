package com.eltonmessias.inventoryservice.warehouse;

import com.eltonmessias.inventoryservice.inventory.Inventory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public record WarehouseRequest(
        UUID tenantId,
        String code,
        String name,
        String address_line1,
        String address_line2,
        String city,
        String state,
        String postalCode,
        String country,
        Boolean isActive
) {
}
