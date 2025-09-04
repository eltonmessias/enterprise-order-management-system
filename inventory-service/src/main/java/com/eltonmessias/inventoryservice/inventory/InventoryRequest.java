package com.eltonmessias.inventoryservice.inventory;

import com.eltonmessias.inventoryservice.warehouse.Warehouse;

import java.util.UUID;

public record InventoryRequest(
        UUID productId,
        UUID warehouseId,
        Integer quantityAvailable,
        Integer quantityPurchased,
        Integer quantityIncoming,
        Integer reorderPoint,
        Integer maxStockLevel
) {
}
