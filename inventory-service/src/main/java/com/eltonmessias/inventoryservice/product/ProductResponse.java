package com.eltonmessias.inventoryservice.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String sku,
        String name,
        String description,
        UUID categoryId
) {
}
