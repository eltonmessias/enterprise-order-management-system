package com.eltonmessias.paymentservice.kafka.producer;

import com.eltonmessias.paymentservice.kafka.events.PaymentConfirmedEvent;
import com.eltonmessias.paymentservice.kafka.events.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentEventProducer {

    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topics.payment-confirmed:payment-confirmed}")
    private String paymentConfirmedTopic;

    @Value("${app.kafka.topics.payment-failed:payment-failed}")
    private String paymentFailedTopic;


    @Async
    public void publishPaymentConfirmed(PaymentConfirmedEvent event) {
        log.info("Publishing payment confirmed event {}", event);
        kafkaTemplate.send(paymentConfirmedTopic, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Successfully published payment confirmed event {}", event);
                    } else {
                        log.error("Failed to publish payment confirmed event {}", event);
                    }
        });
    }

    @Async
    public void publishPaymentFailed(PaymentFailedEvent event) {
        log.info("Publishing payment failed event {}", event);
        kafkaTemplate.send(paymentFailedTopic, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Successfully published payment failed event {}", event);
                    }
                    else {
                        log.error("Failed to publish payment failed event {}", event);
                    }
                });
    }
}
