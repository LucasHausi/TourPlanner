package com.tourplanner.service;

import com.tourplanner.model.Tour;
import com.tourplanner.repository.TourRepository;

public class TourService{
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void saveNewTour(Tour newTour) {
        tourRepository.save(newTour);
    }
}
