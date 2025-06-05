package com.example.stoservice.service;

import com.example.stoservice.dto.request.RequestCreateRequest;
import com.example.stoservice.dto.request.RequestUpdateRequest;
import com.example.stoservice.entity.Request;
import com.example.stoservice.entity.User;
import com.example.stoservice.enums.RequestStatus;
import com.example.stoservice.message.producer.StatusEventProducer;
import com.example.stoservice.repository.RequestRepository;
import com.example.stoservice.repository.UserRepository;
import com.example.stoservice.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.stoservice.util.EntityUtil.findOrThrow;
import static com.example.stoservice.util.MessageUtil.sendStatusEventToKafka;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final StatusEventProducer statusEventProducer;

    @Transactional
    public Request createRequest(RequestCreateRequest createRequest) {
        Request request = new Request();
        request.setTitle(createRequest.title());
        request.setDescription(createRequest.description());
        request.setCurrentStatus(createRequest.status());
        request.setClient(findOrThrow(userRepository, createRequest.clientId(), "User"));
        request.setVehicle(findOrThrow(vehicleRepository, createRequest.vehicleId(), "Vehicle"));
        Request saved = requestRepository.save(request);
        log.info("Request created with id {}", saved.getId());
        return saved;
    }

    @Transactional
    public Request updateRequest(RequestUpdateRequest updateRequest) {
        Request request = findOrThrow(requestRepository, updateRequest.id(), "Request");
        RequestStatus fromStatus = request.getCurrentStatus();
        request.setCurrentStatus(updateRequest.status());
        Request saved = requestRepository.save(request);
        log.info("Request updated with id {}", saved.getId());
        sendStatusEventToKafka(statusEventProducer, fromStatus, updateRequest);
        return saved;
    }

    @Transactional(readOnly = true)
    public Page<Request> getAllRequestsByClientId(Long clientId, Pageable pageable) {
        User client = findOrThrow(userRepository, clientId, "User");
        log.info("Getting requests by Client id {}", client.getId());
        return requestRepository.findByClient(client, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Request> getAllRequestsByMechanicId(Long mechanicId, Pageable pageable) {
        User mechanic = findOrThrow(userRepository, mechanicId, "User");
        log.info("Getting requests by Mechanic id {}", mechanicId);
        return requestRepository.findByMechanic(mechanic, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Request> getAllRequestsByStatus(RequestStatus status, Pageable pageable) {
        log.info("Getting requests by Status {}", status);
        return requestRepository.findByCurrentStatus(status, pageable);
    }

    @Transactional(readOnly = true)
    public Request getRequestById(Long requestId) {
        log.info("Getting request by id {}", requestId);
        return findOrThrow(requestRepository, requestId, "Request");
    }

    @Transactional
    public void deleteRequestById(Long requestId) {
        Request request = findOrThrow(requestRepository, requestId, "Request");
        requestRepository.delete(request);
        log.info("Request deleted with id {}", request.getId());
    }

}
