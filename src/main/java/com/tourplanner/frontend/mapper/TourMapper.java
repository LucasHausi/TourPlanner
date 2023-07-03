package com.tourplanner.frontend.mapper;

import com.tourplanner.frontend.model.Tour;
import com.tourplanner.shared.model.TourDTO;

public interface TourMapper {
    Tour fromDTO(TourDTO tour);
    TourDTO toDTO(Tour tourEntity);
}
