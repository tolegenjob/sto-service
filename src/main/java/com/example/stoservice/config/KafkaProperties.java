package com.example.stoservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka")
public record KafkaProperties(
        Topic topic,
        String bootstrapServers,
        Consumer consumer
) { public record Topic(
        String events
    ) {}
    public record Consumer(
            String eventsGroupId
    ) {}
}
