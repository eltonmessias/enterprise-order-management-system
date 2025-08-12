package com.eltonmessias.orderservice.order;

import com.eltonmessias.orderservice.orderItem.OrderItemResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(

        UUID id,
        UUID tenantId,
        String order_number,
        UUID userId,
        Status status,
        OrderType orderType,
        String currency ,
        BigDecimal subtotal,
        BigDecimal taxAmount,
        BigDecimal shippingAmount,
        BigDecimal totalAmount,

    //    Shipping Address
        String shippingFirstName,
        String shippingLastName,
        String shippingStreetLine1,
        String shippingStreetLine2,
        String shippingCity,
        String shippingState,
        String shippingPostalCode,
        String shippingCountry,

    //    Billing Address
        String billingFirstName,
        String billingLastName,
        String billingStreetLine1,
        String billingStreetLine2,
        String billingCity,
        String billingState,
        String billingPostalCode,
        String billingCountry,

        String notes,
        LocalDateTime orderedAt,
        LocalDateTime confirmedAt,
        LocalDateTime shippedAt,
        LocalDateTime deliveredAt,
        LocalDateTime cancelledAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        List<OrderItemResponse> orderItem
) {
}
