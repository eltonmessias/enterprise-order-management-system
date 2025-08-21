package com.eltonmessias.paymentservice.payment;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentMapper {

    public Payment toPayment(UUID tenantId, PaymentRequest request) {
        return Payment.builder()
                .tenantId(tenantId)
                .orderId(request.orderId())
                .paymentMethod(request.paymentMethod())
                .paymentProvider(request.paymentProvider())
                .amount(request.amount())
                .currency(request.currency())
                .build();
    }

    public PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getTenantId(),
                payment.getOrderId(),
                payment.getPaymentMethod(),
                payment.getPaymentProvider(),
                payment.getExternalPaymentId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus(),
                payment.getPaymentDate(),
                payment.getUpdateDate()
        );
    }
}
