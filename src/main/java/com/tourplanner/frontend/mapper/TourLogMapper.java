package com.tourplanner.frontend.mapper;

import com.tourplanner.frontend.model.TourLog;
import com.tourplanner.shared.model.TourLogDTO;

public interface TourLogMapper {
    TourLog fromDTO(TourLogDTO tourLog);
    TourLogDTO toDTO(TourLog tourLogEntity);
}
