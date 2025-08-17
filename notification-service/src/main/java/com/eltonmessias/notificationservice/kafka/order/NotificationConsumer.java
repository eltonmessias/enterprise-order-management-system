package com.eltonmessias.notificationservice.kafka.order;

import com.eltonmessias.notificationservice.kafka.order.event.OrderCreatedEvent;
import com.eltonmessias.notificationservice.notification.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;

    public NotificationConsumer(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(topics = "order-topic" )
    public void consumeOrderCreated(OrderCreatedEvent event) {
        log.info("Sending notification: Order {} created for customer {}", event.orderNumber(), event.customerId());

        notificationRepository.save(
                Notification.builder()
                        .orderId(event.orderId())
                        .userId(event.userId())
                        .type(NotificationType.EMAIL)
                        .status(NotificationStatus.PENDING)
                        .eventType(NotificationEventType.ORDER_CREATED)
                        .build()
        );


    }
}
