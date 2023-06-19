package com.tourplanner.backend.dal.repository;
import com.tourplanner.backend.dal.entity.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TourRepository extends JpaRepository<TourEntity, UUID>{
}
