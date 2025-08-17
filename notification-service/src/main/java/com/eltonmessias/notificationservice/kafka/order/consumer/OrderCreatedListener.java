package com.eltonmessias.notificationservice.kafka.order.consumer;

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
public class OrderCreatedListener {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "order-topic")
    public void handleOrderCreated(OrderCreatedEvent event) throws MessagingException {
        log.info("Received order created event: {}", event);

        repository.save(
                Notification.builder()
                        .userId(event.userId())
                        .orderId(event.orderId())
                        .type(NotificationType.EMAIL)
                        .status(NotificationStatus.PENDING)
                        .eventType(NotificationEventType.ORDER_CREATED)
                        .build()
        );


        emailService.sendOrderCreatedEmail(
                event.userEmail(),
                event.userName(),
                event.totalAmount(),
                event.orderNumber(),
                event.items()
        );


    }
}
