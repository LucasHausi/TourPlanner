package com.tourplanner.dal.repository;
import com.tourplanner.dal.entity.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TourRepository extends JpaRepository<TourEntity, UUID>{
}
