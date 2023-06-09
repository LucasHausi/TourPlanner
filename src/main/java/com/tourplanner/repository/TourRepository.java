package com.tourplanner.repository;
import com.tourplanner.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TourRepository extends JpaRepository<Tour, UUID>{
}
