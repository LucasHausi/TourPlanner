package com.tourplanner.shared.mapper;

import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.shared.model.Tour;
import org.mapstruct.Mapper;

@Mapper
public interface TourMapper {
    TourEntity toEntity(Tour tour);
    Tour fromEntity(TourEntity tourEntity);
}
