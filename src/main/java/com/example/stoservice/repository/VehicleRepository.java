package com.example.stoservice.repository;

import com.example.stoservice.entity.User;
import com.example.stoservice.entity.Vehicle;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Page<Vehicle> findAllVehiclesByOwner(User owner, Pageable pageable);

    @Query("""
        SELECT v FROM Vehicle v
        WHERE v.id IN (
         SELECT DISTINCT r.vehicle.id FROM Request r WHERE r.mechanic.id = :mechanicId
        )
    """)
    Page<Vehicle> findDistinctVehiclesByMechanicId(@Param("mechanicId") Long mechanicId, Pageable pageable);

    @Override
    @NonNull
    Page<Vehicle> findAll(@NonNull Pageable pageable);
}
