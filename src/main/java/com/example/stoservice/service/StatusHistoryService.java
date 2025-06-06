package com.example.stoservice.service;

import com.example.stoservice.dto.event.StatusEvent;
import com.example.stoservice.entity.Request;
import com.example.stoservice.entity.StatusHistory;
import com.example.stoservice.repository.RequestRepository;
import com.example.stoservice.repository.StatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.stoservice.util.EntityUtil.findOrThrow;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatusHistoryService {

    private final StatusHistoryRepository statusHistoryRepository;
    private final RequestRepository requestRepository;

    @Transactional
    public void createStatusHistory(StatusEvent statusEvent) {
        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setFromStatus(statusEvent.fromStatus());
        statusHistory.setToStatus(statusEvent.toStatus());
        statusHistory.setReason(statusEvent.reason());
        statusHistory.setRequest(
                findOrThrow(requestRepository, statusEvent.requestId(), "Request"));
        statusHistory.setChangedById(statusEvent.changedById());
        statusHistory.setTimestamp(statusEvent.timestamp());
        StatusHistory saved = statusHistoryRepository.save(statusHistory);
        log.info("Saved statusHistory with id: {}", saved.getId());
    }

    @Transactional(readOnly = true)
    public Page<StatusHistory> getAllStatusHistoriesByRequestId(Long requestId, Pageable pageable) {
        Request request = findOrThrow(requestRepository, requestId, "Request");
        log.info("Getting status history by request id: {}", requestId);
        return statusHistoryRepository.findByRequest(request, pageable);
    }

    @Transactional(readOnly = true)
    public Page<StatusHistory> getAllStatusHistories(Pageable pageable) {
        log.info("Getting all status histories");
        return statusHistoryRepository.findAll(pageable);
    }
}
