package com.tourplanner.frontend.mapper;

import com.tourplanner.frontend.model.TourLog;
import com.tourplanner.shared.model.TourLogDTO;
import org.mapstruct.Mapper;

@Mapper
public interface TourLogMapper {
    TourLog fromDTO(TourLogDTO tourLog);
    TourLogDTO toDTO(TourLog tourLogEntity);
}
