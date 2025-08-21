package com.eltonmessias.paymentservice.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID tenantId,
        UUID orderId,
        PaymentMethod paymentMethod,
        PaymentProvider paymentProvider,
        String externalPaymentId,
        BigDecimal amount,
        String currency,
        Status status,
        LocalDateTime paymentDate,
        LocalDateTime updateDate
) {
}
