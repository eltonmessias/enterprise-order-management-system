package com.eltonmessias.inventoryservice.inventory;

import com.eltonmessias.inventoryservice.warehouse.Warehouse;

import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryResponse(
        UUID id,
        String code,
        UUID tenantId,
        UUID productId,
        UUID warehouseId,
        Integer quantityAvailable,
        Integer quantityPurchased,
        Integer quantityIncoming,
        Integer reorderPoint,
        Integer maxStockLevel,
        LocalDateTime lastUpdatedAt
) {
}
