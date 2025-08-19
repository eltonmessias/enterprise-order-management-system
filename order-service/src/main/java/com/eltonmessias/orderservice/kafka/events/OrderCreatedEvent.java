package com.eltonmessias.orderservice.kafka.events;

import com.eltonmessias.orderservice.Product.PurchaseResponse;
import com.eltonmessias.orderservice.orderItem.OrderItem;
import com.eltonmessias.orderservice.orderItem.OrderItemRequest;
import com.eltonmessias.orderservice.orderItem.OrderItemResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        String orderNumber,
        UUID userId,
        String userName,
        String userEmail,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        List<OrderItemResponse> items
) {
}
