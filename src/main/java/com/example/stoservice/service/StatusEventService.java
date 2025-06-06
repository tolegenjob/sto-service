package com.example.stoservice.service;

import com.example.stoservice.dto.event.StatusEvent;
import com.example.stoservice.entity.Request;
import com.example.stoservice.enums.RequestStatus;
import com.example.stoservice.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.stoservice.util.EntityUtil.findOrThrow;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatusEventService {

    private final StatusHistoryService statusHistoryService;
    private final RequestRepository requestRepository;

    @Transactional
    public void handleStatusEvent(StatusEvent statusEvent) {
        statusHistoryService.createStatusHistory(statusEvent);
        if(statusEvent.toStatus().equals(RequestStatus.COMPLETED)) {
            Request request = findOrThrow(requestRepository, statusEvent.requestId(), "Request");
            String email = request.getClient().getEmail();
            String phoneNumber = request.getClient().getPhoneNumber();
            log.info("NOTIFYING CLIENT {}. SENDING MESSAGE TO {} YOUR REQUEST HAS BEEN COMPLETED", email, phoneNumber);
        }
    }

}
