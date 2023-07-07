package com.tourplanner.frontend.bl.mapper;

import com.tourplanner.frontend.bl.model.Tour;
import com.tourplanner.shared.model.TourDTO;
import org.mapstruct.Mapper;

@Mapper(uses = TourLogMapper.class)
public interface TourMapper {
    Tour fromDTO(TourDTO tour);
    TourDTO toDTO(Tour tourEntity);
}
