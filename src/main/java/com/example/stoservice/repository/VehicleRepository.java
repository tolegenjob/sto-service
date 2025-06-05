package com.example.stoservice.repository;

import com.example.stoservice.entity.User;
import com.example.stoservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByOwner(User owner);

}
