package com.tourplanner.repository;

import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.backend.dal.repository.TourRepository;
import com.tourplanner.shared.enums.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class TourRepositoryTest {
    @Autowired
    private TourRepository repo;
    private TourEntity tour1;
    @BeforeEach
    void setUp() {
        tour1 = new TourEntity(UUID.randomUUID(), "TestRoute","Go north","Westfield",
                "Northfield", TransportType.BIKE, 22.0, "22:00", new ArrayList<>(),"Image ---");
        repo.save(tour1);
    }

    @Test
    void findByUUID() {
        var resultTour = repo.findById(tour1.getId()).orElse(null);
        assertNotNull(resultTour);
        assertEquals(tour1,resultTour);
    }
}