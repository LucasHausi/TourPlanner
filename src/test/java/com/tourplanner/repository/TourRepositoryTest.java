package com.tourplanner.repository;

import com.tourplanner.model.Tour;
import com.tourplanner.model.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class TourRepositoryTest {
    @Autowired
    private TourRepository repo;
    private Tour tour1;
    @BeforeEach
    void setUp() {
        tour1 = new Tour(UUID.randomUUID(), "TestRoute","Go north","Westfield",
                "Northfield", TransportType.BIKE, 22.0, "22:00", "Image ---");
        repo.save(tour1);
    }

    @Test
    void findByUUID() {
        var resultTour = repo.findById(tour1.getId()).orElse(null);
        assertNotNull(resultTour);
        assertEquals(tour1,resultTour);
    }
}