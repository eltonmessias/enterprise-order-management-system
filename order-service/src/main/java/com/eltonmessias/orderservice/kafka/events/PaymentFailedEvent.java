package com.eltonmessias.orderservice.kafka.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentFailedEvent(
        UUID orderId,
        UUID paymentId,
        BigDecimal amount,
        LocalDateTime confirmedAt
) {

}
