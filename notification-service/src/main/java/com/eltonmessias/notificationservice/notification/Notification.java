package com.eltonmessias.notificationservice.notification;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Notification {
    @Id
    private String id;
    private UUID orderId;
    private UUID userId;
    private NotificationType type;
    private NotificationStatus status;
    private NotificationEventType eventType;

    LocalDateTime createdAt;

}
