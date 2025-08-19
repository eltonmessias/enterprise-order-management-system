package com.eltonmessias.notificationservice.notification;


import com.eltonmessias.notificationservice.email.EmailService;
import com.eltonmessias.notificationservice.kafka.order.event.OrderCreatedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository repository;
    private final EmailService emailService;


    @Transactional
    public void handleOrderCreatedEvent(OrderCreatedEvent event) throws MessagingException {
        String subject = "Order created Successfully";
        Notification notification = Notification.builder()
                .tenantId(event.tenantId())
                .userId(event.userId())
                .orderId(event.orderId())
                .type(NotificationEventType.ORDER_CREATED)
                .channel(NotificationChannel.EMAIL)
                .recipient(event.userEmail())
                .subject(subject)
                .content("Your order has been created! Order ID: " + event.orderNumber())
                .status(NotificationStatus.PENDING)
                .build();

        repository.save(notification);

        emailService.sendOrderCreatedEmail(event);
    }


}
