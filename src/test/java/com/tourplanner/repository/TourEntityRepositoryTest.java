package com.tourplanner.repository;

import com.tourplanner.backend.dal.repository.TourRepository;
import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.shared.model.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class TourEntityRepositoryTest {
    @Autowired
    private TourRepository repo;
    private TourEntity tourEntity1;
    @BeforeEach
    void setUp() {
        tourEntity1 = new TourEntity(UUID.randomUUID(), "TestRoute","Go north","Westfield",
                "Northfield", TransportType.BIKE, 22.0, "22:00", "Image ---");
        repo.save(tourEntity1);
    }

    @Test
    void findByUUID() {
        var resultTour = repo.findById(tourEntity1.getId()).orElse(null);
        assertNotNull(resultTour);
        assertEquals(tourEntity1,resultTour);
    }
}