package com.example.stoservice.controller;

import com.example.stoservice.dto.request.RequestCreateRequest;
import com.example.stoservice.dto.request.RequestUpdateRequest;
import com.example.stoservice.dto.response.RequestCreateResponse;
import com.example.stoservice.dto.response.RequestResponse;
import com.example.stoservice.dto.response.RequestUpdateResponse;
import com.example.stoservice.entity.Request;
import com.example.stoservice.enums.RequestStatus;
import com.example.stoservice.security.AccessService;
import com.example.stoservice.service.RequestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requests")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final AccessService accessService;

    @PostMapping
    public ResponseEntity<RequestCreateResponse> createRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid RequestCreateRequest createRequest
    ) {
        RequestCreateResponse response = RequestCreateResponse.toDto(
                requestService.createRequest(userDetails, createRequest));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestUpdateResponse> updateRequestStatus(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody @Valid RequestUpdateRequest updateRequest,
            @RequestParam(required = false) Long mechanicId
    ) {
        if (!accessService.canUpdateRequest(userDetails, updateRequest.status(), id)) {
            return ResponseEntity.status(403).build();
        }
        RequestUpdateResponse response = RequestUpdateResponse.toDto(
                requestService.updateRequestById(id, updateRequest, mechanicId, userDetails));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestResponse> getRequestById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        if (!accessService.canAccessRequest(userDetails, id)) {
            return ResponseEntity.status(403).build();
        }
        RequestResponse response = RequestResponse.toDto(requestService.getRequestById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<RequestResponse>> getAllRequestsByStatus(
            @RequestParam(required = false) RequestStatus status,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Request> requests = (status != null)
                ? requestService.getAllRequestsByStatus(status, pageable)
                : requestService.getAllRequests(pageable);
        Page<RequestResponse> responses = requests.map(RequestResponse::toDto);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/mine")
    public ResponseEntity<Page<RequestResponse>> getMyRequests(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<RequestResponse> responses = requestService.getAllRequestsByCurrentUser(userDetails, pageable)
                .map(RequestResponse::toDto);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        if (!accessService.canDeleteRequest(userDetails)) {
            return ResponseEntity.status(403).build();
        }
        requestService.deleteRequestById(id);
        return ResponseEntity.noContent().build();
    }

}
