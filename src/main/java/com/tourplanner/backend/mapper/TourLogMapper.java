package com.tourplanner.backend.mapper;

import com.tourplanner.backend.dal.entity.TourLogEntity;
import com.tourplanner.shared.model.TourLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TourLogMapper {

    @Mapping(source = "tour", target = "tourEntity")
    TourLogEntity toEntity(TourLog tourLog);
    TourLog fromEntity(TourLogEntity tourLogEntity);
}
