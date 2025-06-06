package com.example.stoservice.service;

import com.example.stoservice.dto.request.RequestCreateRequest;
import com.example.stoservice.dto.request.RequestUpdateRequest;
import com.example.stoservice.entity.Request;
import com.example.stoservice.entity.User;
import com.example.stoservice.enums.RequestStatus;
import com.example.stoservice.enums.UserRole;
import com.example.stoservice.message.producer.StatusEventProducer;
import com.example.stoservice.repository.RequestRepository;
import com.example.stoservice.repository.UserRepository;
import com.example.stoservice.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.stoservice.util.EntityUtil.findOrThrow;
import static com.example.stoservice.util.EntityUtil.findUserByEmailOrThrow;
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
    public Request createRequest(UserDetails userDetails, RequestCreateRequest createRequest) {
        Request request = new Request();
        request.setTitle(createRequest.title());
        request.setDescription(createRequest.description());
        request.setCurrentStatus(RequestStatus.NEW);
        User client = findUserByEmailOrThrow(userRepository, userDetails.getUsername());
        request.setClient(client);
        request.setVehicle(findOrThrow(vehicleRepository, createRequest.vehicleId(), "Vehicle"));
        Request saved = requestRepository.save(request);
        log.info("Request created with id {}", saved.getId());
        return saved;
    }

    @Transactional
    public Request updateRequestById(Long id, RequestUpdateRequest updateRequest, Long mechanicId, UserDetails userDetails) {
        Request request = findOrThrow(requestRepository, id, "Request");
        User user  = findUserByEmailOrThrow(userRepository, userDetails.getUsername());
        RequestStatus fromStatus = request.getCurrentStatus();
        RequestStatus toStatus = updateRequest.status();
        String reason = updateRequest.reason();
        if(toStatus.equals(RequestStatus.IN_PROGRESS)) {
            User mechanic = findOrThrow(userRepository, mechanicId, "User");
            if(mechanic.getRole() != UserRole.MECHANIC) {
                throw new AccessDeniedException("User is not a mechanic");
            }
            request.setMechanic(mechanic);
        }
        request.setCurrentStatus(toStatus);
        Request saved = requestRepository.save(request);
        log.info("Request updated with id {}", saved.getId());
        sendStatusEventToKafka(
                statusEventProducer,
                id,
                fromStatus,
                toStatus,
                reason,
                user.getId(),
                saved.getUpdatedAt());
        return saved;
    }

    @Transactional(readOnly = true)
    public Page<Request> getAllRequestsByCurrentUser(UserDetails userDetails, Pageable pageable) {
        User user = findUserByEmailOrThrow(userRepository, userDetails.getUsername());
        log.info("Getting requests by current user {}", user.getEmail());

        return (user.getRole() == UserRole.MECHANIC)
            ? requestRepository.findAllRequestsByMechanic(user, pageable)
            : requestRepository.findAllRequestsByClient(user, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Request> getAllRequestsByStatus(RequestStatus status, Pageable pageable) {
        log.info("Getting requests by Status {}", status);
        return requestRepository.findAllRequestsByCurrentStatus(status, pageable);
    }

    @Transactional(readOnly = true)
    public Request getRequestById(Long id) {
        log.info("Getting request by id {}", id);
        return findOrThrow(requestRepository, id, "Request");
    }

    @Transactional(readOnly = true)
    public Page<Request> getAllRequests(Pageable pageable) {
        log.info("Getting all requests");
        return requestRepository.findAll(pageable);
    }

    @Transactional
    public void deleteRequestById(Long requestId) {
        Request request = findOrThrow(requestRepository, requestId, "Request");
        requestRepository.delete(request);
        log.info("Request deleted with id {}", request.getId());
    }

}
