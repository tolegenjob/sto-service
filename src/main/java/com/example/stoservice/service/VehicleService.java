package com.example.stoservice.service;

import com.example.stoservice.dto.request.VehicleCreateRequest;
import com.example.stoservice.dto.request.VehicleUpdateRequest;
import com.example.stoservice.entity.User;
import com.example.stoservice.entity.Vehicle;
import com.example.stoservice.repository.UserRepository;
import com.example.stoservice.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.stoservice.util.EntityUtil.findOrThrow;
import static com.example.stoservice.util.EntityUtil.findVehicleByOwnerOrThrow;

@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Vehicle createVehicle(VehicleCreateRequest request) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(request.brand());
        vehicle.setModel(request.model());
        vehicle.setColor(request.color());
        vehicle.setLicensePlate(request.licensePlate());
        vehicle.setYear(request.year());
        vehicle.setOwner(findOrThrow(userRepository, request.ownerId(), "User"));
        Vehicle saved = vehicleRepository.save(vehicle);
        log.info("Vehicle created with id: {}", saved.getId());
        return saved;
    }

    @Transactional
    public Vehicle updateVehicle(VehicleUpdateRequest request) {
        Vehicle vehicle = findOrThrow(vehicleRepository, request.id(), "Vehicle");
        vehicle.setColor(request.color());
        vehicle.setOwner(findOrThrow(userRepository, request.ownerId(), "User"));
        Vehicle saved = vehicleRepository.save(vehicle);
        log.info("Vehicle updated with id: {}", saved.getId());
        return saved;
    }

    @Transactional(readOnly = true)
    public Vehicle getVehicleByOwner(Long ownerId) {
        User owner = findOrThrow(userRepository, ownerId, "User");
        log.info("Getting Vehicle by owner id: {}", owner.getId());
        return findVehicleByOwnerOrThrow(vehicleRepository, owner);
    }

    public void 
}
