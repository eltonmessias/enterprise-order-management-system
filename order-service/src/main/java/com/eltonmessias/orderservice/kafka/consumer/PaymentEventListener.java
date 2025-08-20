package com.eltonmessias.orderservice.kafka.consumer;

import com.eltonmessias.orderservice.exception.OrderNotFoundException;
import com.eltonmessias.orderservice.kafka.events.PaymentConfirmedEvent;
import com.eltonmessias.orderservice.kafka.events.PaymentFailedEvent;
import com.eltonmessias.orderservice.order.Order;
import com.eltonmessias.orderservice.order.OrderRepository;
import com.eltonmessias.orderservice.order.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {


    private final OrderRepository orderRepository;

    @KafkaListener(topics = "${app.kafka.topics.payment-processed}", groupId = "order-service")
    public void consumePaymentConfirmed(PaymentConfirmedEvent event) {
        log.info("Payment confirmed for order {}", event.orderId());

        Order order = orderRepository.findById(event.orderId()).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        order.setStatus(Status.CONFIRMED);
        orderRepository.save(order);

        log.info("Order {} status updated to Confirmed", event.orderId());
    }

    @KafkaListener(topics = "${app.kafka.topics.payment-failed}", groupId = "order-service")
    public void consumePaymentFailed(PaymentFailedEvent event) {
        log.info("Payment failed for order {}", event.orderId());

        Order order = orderRepository.findById(event.orderId()).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        order.setStatus(Status.FAILED);
        orderRepository.save(order);
        log.info("Order {} status updated to Failed", event.orderId());
    }

}
