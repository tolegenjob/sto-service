package com.example.stoservice.service;

import com.example.stoservice.dto.request.VehicleCreateRequest;
import com.example.stoservice.dto.request.VehicleUpdateRequest;
import com.example.stoservice.entity.User;
import com.example.stoservice.entity.Vehicle;
import com.example.stoservice.enums.UserRole;
import com.example.stoservice.repository.UserRepository;
import com.example.stoservice.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.stoservice.util.EntityUtil.findOrThrow;
import static com.example.stoservice.util.EntityUtil.findUserByEmailOrThrow;

@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Vehicle createVehicle(UserDetails userDetails, VehicleCreateRequest request) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(request.brand());
        vehicle.setModel(request.model());
        vehicle.setColor(request.color());
        vehicle.setLicensePlate(request.licensePlate());
        vehicle.setYear(request.year());
        User owner = findUserByEmailOrThrow(userRepository, userDetails.getUsername());
        vehicle.setOwner(owner);
        Vehicle saved = vehicleRepository.save(vehicle);
        log.info("Vehicle created with id: {}", saved.getId());
        return saved;
    }

    @Transactional
    public Vehicle updateVehicle(Long id, VehicleUpdateRequest request) {
        Vehicle vehicle = findOrThrow(vehicleRepository, id, "Vehicle");
        vehicle.setColor(request.color());
        Vehicle saved = vehicleRepository.save(vehicle);
        log.info("Vehicle updated with id: {}", saved.getId());
        return saved;
    }

    @Transactional(readOnly = true)
    public Vehicle getVehicleById(Long id) {
        log.info("Getting vehicle by id {}", id);
        return findOrThrow(vehicleRepository, id, "Vehicle");
    }

    @Transactional(readOnly = true)
    public Page<Vehicle> getAllVehiclesByCurrentUser(UserDetails userDetails, Pageable pageable) {
        log.info("Getting all vehicle by current user: {}", userDetails.getUsername());
        User user = findUserByEmailOrThrow(userRepository, userDetails.getUsername());
        return (user.getRole() == UserRole.MECHANIC)
                ? vehicleRepository.findDistinctVehiclesByMechanicId(user.getId(), pageable)
                : vehicleRepository.findAllVehiclesByOwner(user, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Vehicle> getAllVehicles(Pageable pageable) {
        log.info("Getting all vehicles");
        return vehicleRepository.findAll(pageable);
    }

    @Transactional
    public void deleteVehicleById(Long id) {
        Vehicle vehicle = findOrThrow(vehicleRepository, id, "Vehicle");
        vehicleRepository.delete(vehicle);
        log.info("Vehicle deleted by owner id: {}", vehicle);
    }

}
