package com.example.stoservice.util;

import com.example.stoservice.entity.User;
import com.example.stoservice.entity.Vehicle;
import com.example.stoservice.exception.NotFoundException;
import com.example.stoservice.repository.UserRepository;
import com.example.stoservice.repository.VehicleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntityUtil {

    public static <T, ID> T findOrThrow(JpaRepository<T, ID> repository, ID id, String entityName) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("%s with id %s not found".formatted(entityName, id)));
    }

    public static User findUserByEmailOrThrow(UserRepository userRepository, String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("User with email %s not found".formatted(email)));
    }

    public static Vehicle findVehicleByOwnerOrThrow(VehicleRepository vehicleRepository, User owner) {
        return vehicleRepository.findByOwner(owner)
                .orElseThrow(() ->
                        new NotFoundException("Vehicle with owner id %d not found".formatted(owner.getId())));
    }
}
