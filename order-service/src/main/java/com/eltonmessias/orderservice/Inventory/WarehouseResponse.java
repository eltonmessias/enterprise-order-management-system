package com.eltonmessias.orderservice.Inventory;

import java.util.UUID;

public record WarehouseResponse(
        UUID id,
        UUID tenantId,
        String code,
        String name
) {
}
