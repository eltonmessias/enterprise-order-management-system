package com.eltonmessias.orderservice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
@Slf4j
public class kafkaOrderTopicConfig {

    @Value("${app.kafka.topics.order-created:order-created}")
    private String orderCreatedTopic;

    @Value("${app.kafka.topics.order-updated}")
    private String orderUpdatedTopic;

    @Value("${app.kafka.topics.order-cancelled:order-cancelled}")
    private String orderCancelledTopic;

    @Value("${app.kafka.default.partitions}")
    private int partitions;

    @Value("${app.kafka.default.replicas}")
    private int replicas;

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name(orderCreatedTopic)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    public NewTopic orderUpdatedTopic() {
        return TopicBuilder.name(orderUpdatedTopic)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    public NewTopic orderCancelledTopic() {
        return TopicBuilder.name(orderCancelledTopic)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

}
