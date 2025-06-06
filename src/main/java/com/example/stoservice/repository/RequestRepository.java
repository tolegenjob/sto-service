package com.example.stoservice.repository;

import com.example.stoservice.entity.Request;
import com.example.stoservice.entity.User;
import com.example.stoservice.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Page<Request> findAllRequestsByClient(User client, Pageable pageable);

    Page<Request> findAllRequestsByCurrentStatus(RequestStatus currentStatus, Pageable pageable);

    Page<Request> findAllRequestsByMechanic(User mechanic, Pageable pageable);

    @Override
    @NonNull
    Page<Request> findAll(@NonNull Pageable pageable);
}
