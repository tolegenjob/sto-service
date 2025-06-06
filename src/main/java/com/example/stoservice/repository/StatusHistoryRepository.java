package com.example.stoservice.repository;

import com.example.stoservice.entity.Request;
import com.example.stoservice.entity.StatusHistory;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {

    Page<StatusHistory> findByRequest(Request request, Pageable pageable);

    @Override
    @NonNull
    Page<StatusHistory> findAll(@NonNull Pageable pageable);
}
