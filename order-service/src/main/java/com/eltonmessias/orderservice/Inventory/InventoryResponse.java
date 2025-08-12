package com.eltonmessias.orderservice.Inventory;

import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryResponse(
        UUID id,
        String code,
        UUID tenantId,
        UUID productId,
        UUID warehouseId,
        Integer quantityAvailable
) {

}
