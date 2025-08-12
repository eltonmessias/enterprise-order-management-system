package com.eltonmessias.orderservice.orderItem;

import com.eltonmessias.orderservice.order.Order;
import lombok.Builder;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record OrderItemResponse(
        UUID id,
        UUID orderId,
        UUID productId,
        String sku,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice,
        UUID warehouseId,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
