package com.example.stoservice.message.consumer;

import com.example.stoservice.dto.event.StatusEvent;
import com.example.stoservice.service.StatusEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatusEventConsumer {

    private final StatusEventService statusEventService;

    @KafkaListener(
            topics = "${spring.kafka.topic.events}",
            groupId = "${spring.kafka.consumer.events-group-id}"
    )
    public void receiveEvent(StatusEvent statusEvent) {
        log.info("Received StatusEvent: {} from topic sto-service.events",
                statusEvent);
        statusEventService.handleStatusEvent(statusEvent);
    }

}
