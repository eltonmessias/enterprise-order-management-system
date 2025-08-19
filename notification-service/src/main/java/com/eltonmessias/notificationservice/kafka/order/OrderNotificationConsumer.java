package com.eltonmessias.notificationservice.kafka.order;

import com.eltonmessias.notificationservice.email.EmailService;
import com.eltonmessias.notificationservice.kafka.order.event.OrderCreatedEvent;
import com.eltonmessias.notificationservice.notification.*;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class OrderNotificationConsumer {

    private final EmailService emailService;
    private final NotificationService notificationService;

    @KafkaListener(topics = "${app.kafka.topics.order-created}", groupId = "notification-service")
    public void consumeOrderCreated(OrderCreatedEvent event) throws MessagingException {
        log.info("Received order created event: {}", event);
       notificationService.handleOrderCreatedEvent(event);
    }
}
