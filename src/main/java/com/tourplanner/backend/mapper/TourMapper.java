package com.tourplanner.backend.mapper;

import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.shared.model.Tour;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TourMapper {
    TourEntity toEntity(Tour tour);
    Tour fromEntity(TourEntity tourEntity);
}
