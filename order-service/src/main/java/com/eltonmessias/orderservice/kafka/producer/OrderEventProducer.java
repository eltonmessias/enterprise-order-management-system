package com.eltonmessias.orderservice.kafka.producer;

import com.eltonmessias.orderservice.kafka.events.OrderCancelledEvent;
import com.eltonmessias.orderservice.kafka.events.OrderCreatedEvent;
import com.eltonmessias.orderservice.kafka.events.OrderUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.concurrent.CompletedFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Value("${app.kafka.topics.order-created:order-created}")
    private String orderCreatedTopic;

    @Value("${app.kafka.topics.order-updated:order-updated}")
    private String orderUpdatedTopic;

    @Value("${app.kafka.topics.order-cancelled:order-cancelled}")
    private String orderCancelledTopic;


    @Async
    public void publishOrderCreated(OrderCreatedEvent event) {
        log.info("Publishing order created event for order: {}", event.orderNumber());
        kafkaTemplate.send("order-created", event.orderNumber(), event)
                .whenComplete((result, ex) -> {
                    if(ex == null) {
                        log.info("Order created event sent successfully for order: {} with offset: {}",
                                event.orderNumber(), result.getRecordMetadata().offset());
                    } else {
                        log.error("Failed to send order created event: {}", event.orderNumber(), ex);
                    }
                });
    }



    @Async
    public CompletableFuture<SendResult<String, Object>> publishOrderUpdated(OrderUpdateEvent event) {
        log.info("Publishing order update event for order: {} ", event.orderNumber());

        return kafkaTemplate.send(orderUpdatedTopic, event.orderNumber(), event)
                .whenComplete((result, ex) -> {
                    if(ex == null) {
                        log.info("Order updated event sent successfully for order: {}", event.orderNumber());
                    } else {
                        log.error("Failed to send order updated event for order: {}", event.orderNumber(), ex);
                    }
                });
    }

    @Async
    public CompletableFuture<SendResult<String, Object>> publishOrderCancelled(OrderCancelledEvent event) {
        log.info("Publishing order cancelled event for order: {} ", event.orderNumber());
        return kafkaTemplate.send(orderCancelledTopic, event.orderNumber(), event)
                .whenComplete((result, ex) -> {
                    if(ex == null) {
                        log.info("Order cancelled event sent successfully for order: {}", event.orderNumber());
                    } else {
                        log.error("Failed to send order cancelled event for order: {}", event.orderNumber(), ex);
                    }
                });
    }

}
