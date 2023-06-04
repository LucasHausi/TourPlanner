package com.tourplanner.tourplanner.repository;

import com.tourplanner.tourplanner.model.TourLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TourLogRepository extends JpaRepository<TourLog, UUID> {
}
