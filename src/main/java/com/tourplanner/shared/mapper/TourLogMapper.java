package com.tourplanner.shared.mapper;

import com.tourplanner.backend.dal.entity.TourLogEntity;
import com.tourplanner.shared.model.TourLog;
import org.mapstruct.Mapper;

@Mapper
public interface TourLogMapper {
    TourLogEntity toEntity(TourLog tourLog);
    TourLog tourLogEntityToTourLog(TourLogEntity tourLogEntity);
}
