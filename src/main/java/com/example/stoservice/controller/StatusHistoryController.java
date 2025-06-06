package com.example.stoservice.controller;

import com.example.stoservice.dto.response.StatusHistoryResponse;
import com.example.stoservice.entity.StatusHistory;
import com.example.stoservice.service.StatusHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status-histories")
@RequiredArgsConstructor
public class StatusHistoryController {

    private final StatusHistoryService statusHistoryService;

    @GetMapping
    public ResponseEntity<Page<StatusHistoryResponse>> getAllStatusHistoriesByRequestId(
            @RequestParam(required = false) Long requestId,
            @PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<StatusHistory> statusHistories = (requestId != null)
                ? statusHistoryService.getAllStatusHistoriesByRequestId(requestId, pageable)
                : statusHistoryService.getAllStatusHistories(pageable);
        Page<StatusHistoryResponse> responses = statusHistories.map(StatusHistoryResponse::toDto);
        return ResponseEntity.ok(responses);
    }

}
