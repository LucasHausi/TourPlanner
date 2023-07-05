package com.tourplanner.frontend.mapper;

import com.tourplanner.frontend.model.Tour;
import com.tourplanner.shared.model.TourDTO;
import org.mapstruct.Mapper;

@Mapper(uses = TourLogMapper.class)
public interface TourMapper {
    Tour fromDTO(TourDTO tour);
    TourDTO toDTO(Tour tourEntity);
}
