package com.tourplanner.backend.dal.repository;

import com.tourplanner.backend.dal.entity.TourLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TourLogRepository extends JpaRepository<TourLogEntity, UUID> {
List<TourLogEntity> getTourLogById(UUID tourId);
}
