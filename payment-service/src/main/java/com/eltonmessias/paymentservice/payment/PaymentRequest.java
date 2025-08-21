package com.eltonmessias.paymentservice.payment;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
        UUID orderId,
        PaymentMethod paymentMethod,
        PaymentProvider paymentProvider,
        BigDecimal amount,
        String currency
) {
}
