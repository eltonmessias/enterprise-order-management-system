package com.eltonmessias.orderservice.order;

import com.eltonmessias.orderservice.orderItem.OrderItem;
import com.eltonmessias.orderservice.orderItem.OrderItemRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderRequest(
        UUID userId,
        Status status,
        OrderType orderType,

        List<OrderItemRequest> orderItems,

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

        String notes
) {
}
