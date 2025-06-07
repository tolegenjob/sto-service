package com.example.stoservice.security;

import com.example.stoservice.entity.Request;
import com.example.stoservice.entity.User;
import com.example.stoservice.entity.Vehicle;
import com.example.stoservice.enums.RequestStatus;
import com.example.stoservice.enums.UserRole;
import com.example.stoservice.repository.RequestRepository;
import com.example.stoservice.repository.UserRepository;
import com.example.stoservice.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.example.stoservice.util.EntityUtil.findOrThrow;
import static com.example.stoservice.util.EntityUtil.findUserByEmailOrThrow;

@Service
@RequiredArgsConstructor
public class AccessService {

    private final RequestRepository requestRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public boolean canAccessRequest(UserDetails userDetails, Long requestId) {
        User currentUser = findUserByEmailOrThrow(userRepository, userDetails.getUsername());
        Request request = findOrThrow(requestRepository, requestId, "Request");
        return switch (currentUser.getRole()) {
            case ADMIN, MANAGER -> true;
            case MECHANIC -> request.getMechanic() != null
                    && request.getMechanic().equals(currentUser);
            case CLIENT -> request.getClient().equals(currentUser);
        };
    }

    public boolean canUpdateRequest(UserDetails userDetails, RequestStatus status, Long requestId) {
        User currentUser = findUserByEmailOrThrow(userRepository, userDetails.getUsername());
        Request request = findOrThrow(requestRepository, requestId, "Request");
        return switch (currentUser.getRole()) {
            case ADMIN -> true;
            case MANAGER -> status != RequestStatus.REPAIRING && status != RequestStatus.COMPLETED;
            case MECHANIC -> (request.getMechanic().equals(currentUser)) && (status == RequestStatus.REPAIRING || status == RequestStatus.COMPLETED);
            default -> false;
        };
    }

    public boolean canDeleteRequest(UserDetails userDetails) {
        User currentUser = findUserByEmailOrThrow(userRepository, userDetails.getUsername());
        return switch (currentUser.getRole()) {
            case ADMIN, MANAGER -> true;
            case MECHANIC, CLIENT -> false;
        };
    }

    public boolean canAccessVehicle(UserDetails userDetails, Long vehicleId) {
        User currentUser = findUserByEmailOrThrow(userRepository, userDetails.getUsername());
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        return switch (currentUser.getRole()) {
            case ADMIN, MANAGER -> true;
            case CLIENT -> vehicle.getOwner().getId().equals(currentUser.getId());
            case MECHANIC -> false;
        };
    }

    public boolean canCreateUser(UserDetails userDetails, UserRole role) {
        User currentUser = findUserByEmailOrThrow(userRepository, userDetails.getUsername());
        return switch (currentUser.getRole()) {
            case ADMIN -> true;
            case MANAGER -> role == UserRole.MECHANIC;
            case CLIENT, MECHANIC -> false;
        };
    }

}
