package com.eltonmessias.orderservice.payment;

import com.eltonmessias.orderservice.user.UserResponse;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
        BigDecimal totalAmount,
        UUID orderId,
        String orderNumber,
        UserResponse user
) {
}
