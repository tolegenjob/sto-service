package com.example.stoservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka")
public record KafkaProperties(
        Topic topic,
        String bootstrapServers,
        Consumer consumer
) { public record Topic(
        String events,
        String dlq
    ) {}
    public record Consumer(
            String dlqGroupId,
            String eventsGroupId
    ) {}
}
