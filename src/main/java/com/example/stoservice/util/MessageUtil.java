package com.example.stoservice.util;

import com.example.stoservice.dto.event.StatusEvent;
import com.example.stoservice.enums.RequestStatus;
import com.example.stoservice.message.producer.StatusEventProducer;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class MessageUtil {

    public static void sendStatusEventToKafka(
            StatusEventProducer statusEventProducer,
            Long requestId,
            RequestStatus fromStatus,
            RequestStatus toStatus,
            String reason,
            Long changedById,
            LocalDateTime timestamp
    ) {
        StatusEvent statusEvent = new StatusEvent(
                requestId, fromStatus, toStatus, reason, changedById, timestamp);
        log.info("StatusEvent was created: {}", statusEvent);
        statusEventProducer.sendStatusEvent(statusEvent);
    }

}
