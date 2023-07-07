package com.tourplanner.frontend.bl.mapper;

import com.tourplanner.frontend.bl.model.TourLog;
import com.tourplanner.shared.model.TourLogDTO;
import org.mapstruct.Mapper;

@Mapper
public interface TourLogMapper {
    TourLog fromDTO(TourLogDTO tourLog);
    TourLogDTO toDTO(TourLog tourLogEntity);
}
