package com.eltonmessias.orderservice.kafka.events;

import com.eltonmessias.orderservice.order.Status;
import com.eltonmessias.orderservice.orderItem.OrderItemResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderStatusUpdatedEvent(
        UUID orderId,
        Status status,
        String orderNumber,
        UUID userId,
        String userName,
        String userEmail,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        List<OrderItemResponse> items
) {

}
