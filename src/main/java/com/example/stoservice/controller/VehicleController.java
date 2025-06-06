package com.example.stoservice.controller;

import com.example.stoservice.dto.request.VehicleCreateRequest;
import com.example.stoservice.dto.request.VehicleUpdateRequest;
import com.example.stoservice.dto.response.VehicleCreateResponse;
import com.example.stoservice.dto.response.VehicleResponse;
import com.example.stoservice.dto.response.VehicleUpdateResponse;
import com.example.stoservice.security.AccessService;
import com.example.stoservice.service.VehicleService;
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
@RequestMapping("/api/vehicles")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final AccessService accessService;

    @PostMapping
    public ResponseEntity<VehicleCreateResponse> createVehicle(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid VehicleCreateRequest createRequest
    ) {
        VehicleCreateResponse response = VehicleCreateResponse.toDto(
                vehicleService.createVehicle(userDetails, createRequest));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleUpdateResponse> updateVehicleById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody @Valid VehicleUpdateRequest updateRequest
    ) {
        if (!accessService.canAccessVehicle(userDetails, id)) {
            return ResponseEntity.status(403).build();
        }
        VehicleUpdateResponse response = VehicleUpdateResponse
                .toDto(vehicleService.updateVehicle(id, updateRequest));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        if (!accessService.canAccessVehicle(userDetails, id)) {
            return ResponseEntity.status(403).build();
        }
        VehicleResponse response = VehicleResponse.toDto(vehicleService.getVehicleById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mine")
    public ResponseEntity<Page<VehicleResponse>> getMyVehicles(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<VehicleResponse> responses = vehicleService.getAllVehiclesByCurrentUser(userDetails, pageable)
                .map(VehicleResponse::toDto);
        return ResponseEntity.ok(responses);
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponse>> getAllVehicles(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<VehicleResponse> responses = vehicleService.getAllVehicles(pageable)
                .map(VehicleResponse::toDto);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ){
        if (!accessService.canAccessVehicle(userDetails, id)) {
            return ResponseEntity.status(403).build();
        }
        vehicleService.deleteVehicleById(id);
        return ResponseEntity.noContent().build();
    }

}
