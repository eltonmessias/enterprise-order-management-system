package com.eltonmessias.notificationservice.kafka.order.event;

import org.springframework.core.annotation.Order;

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
        List<OrderItem> items
) {

    public record OrderItem(
            UUID productId,
            String productName,
            int quantity,
            BigDecimal unitPrice,
            BigDecimal total
    ){}

}
