package com.example.stoservice.message.producer;

import com.example.stoservice.config.KafkaProperties;
import com.example.stoservice.dto.event.StatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatusEventProducer {

    private final KafkaTemplate<String, StatusEvent> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public void sendStatusEvent(StatusEvent statusEvent) {
        kafkaTemplate.send(kafkaProperties.topic().events(), statusEvent);
        log.info("Event was sent to Kafka topic [{}]: {}",
                kafkaProperties.topic().events(),
                statusEvent);
    }

}
