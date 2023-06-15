package com.tourplanner.dal.repository;

import com.tourplanner.model.TourLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TourLogRepository extends JpaRepository<TourLog, UUID> {
List<TourLog> getTourLogByTourId(UUID tourId);
}
