package com.eltonmessias.paymentservice.payment;

import com.eltonmessias.paymentservice.exception.PaymentNotFoundException;
import com.eltonmessias.paymentservice.kafka.events.PaymentConfirmedEvent;
import com.eltonmessias.paymentservice.kafka.producer.PaymentEventProducer;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {


    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer paymentEventProducer;

    public PaymentResponse createPayment(UUID tenantId, @Valid PaymentRequest paymentRequest) {
        Payment payment = paymentMapper.toPayment(tenantId, paymentRequest);
        payment.setTenantId(tenantId);
        return paymentMapper.toPaymentResponse(payment);
    }

    public void confirmPayment(UUID tenantId, UUID paymentId, String externalPaymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException(
                "Payment not found"));
        payment.setExternalPaymentId(externalPaymentId);
        payment.setStatus(Status.CONFIRMED);
        paymentRepository.save(payment);

        paymentEventProducer.publishPaymentConfirmed(
                new PaymentConfirmedEvent(
                        payment.getTenantId(),
                        payment.getOrderId(),
                        payment.getId(),
                        payment.getAmount(),
                        payment.getPaymentDate()
                )
        );
    }


    public List<PaymentResponse> findAllPaymentsByTenantId(UUID tenantId) {
        List<Payment> payments = paymentRepository.findAllByTenantId(tenantId);
        if (payments.isEmpty()) {
            throw new PaymentNotFoundException("Payments not found");
        }
        return payments.stream().map(paymentMapper::toPaymentResponse).collect(Collectors.toList());
    }
}
