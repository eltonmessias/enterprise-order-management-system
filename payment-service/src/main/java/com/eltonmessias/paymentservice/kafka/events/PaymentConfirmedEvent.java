package com.eltonmessias.paymentservice.kafka.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentConfirmedEvent(
        UUID tenantId,
        UUID orderId,
        UUID paymentId,
        BigDecimal amount,
        LocalDateTime confirmedAt
) {
}
