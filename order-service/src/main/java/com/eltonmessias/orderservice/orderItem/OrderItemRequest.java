package com.eltonmessias.orderservice.orderItem;

import java.util.UUID;

public record OrderItemRequest(
        UUID productId,
        UUID orderId,
        Integer quantity
) {
}
