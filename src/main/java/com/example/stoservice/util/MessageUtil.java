package com.example.stoservice.util;

import com.example.stoservice.dto.event.StatusEvent;
import com.example.stoservice.dto.request.RequestUpdateRequest;
import com.example.stoservice.enums.RequestStatus;
import com.example.stoservice.message.producer.StatusEventProducer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageUtil {

    public static void sendStatusEventToKafka(
            StatusEventProducer eventLogProducer,
            RequestStatus fromStatus,
            RequestUpdateRequest updateRequest) {
        StatusEvent statusEvent = new StatusEvent(
                fromStatus, updateRequest);
        log.info("StatusEvent was created: {}", statusEvent);
        eventLogProducer.sendStatusEvent(statusEvent);
    }

}
