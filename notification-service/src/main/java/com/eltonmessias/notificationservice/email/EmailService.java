package com.eltonmessias.notificationservice.email;

import com.eltonmessias.notificationservice.kafka.order.event.OrderCreatedEvent;
import com.eltonmessias.notificationservice.notification.Notification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static com.eltonmessias.notificationservice.email.EmailTemplates.ORDER_CONFIRMATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendOrderCreatedEmail(
            OrderCreatedEvent event) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        mimeMessageHelper.setFrom("eltonmessias10@gmail.com");

        final String templateName = ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("userName", event.userName());
        variables.put("totalAmount", event.totalAmount());
        variables.put("orderNumber", event.orderNumber());
        variables.put("orderItems", event.items());

        Context context = new Context();
        context.setVariables(variables);
        mimeMessageHelper.setSubject(ORDER_CONFIRMATION.getSubject());


        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);

            mimeMessageHelper.setTo(event.userEmail());
            mailSender.send(mimeMessage);
            log.info("Email sent to " + event.userEmail());
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send email to " + event.userEmail(), e);
        }
    }
}
