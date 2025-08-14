package com.eltonmessias.orderservice.kafka.events;

import com.eltonmessias.orderservice.Product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderCancelledEvent(
        String orderNumber,
        UUID customerId,
        BigDecimal totalAmount,
        List<PurchaseResponse> items
) {
}
