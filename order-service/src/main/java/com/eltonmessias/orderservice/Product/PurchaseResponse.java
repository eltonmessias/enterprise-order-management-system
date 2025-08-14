package com.eltonmessias.orderservice.Product;

import com.eltonmessias.orderservice.orderItem.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PurchaseResponse(
        UUID productId,
        String sku,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {
}
