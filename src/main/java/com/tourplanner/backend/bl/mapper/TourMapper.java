package com.tourplanner.backend.bl.mapper;

import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.shared.model.TourDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = TourLogMapper.class)
@Component
public interface TourMapper {
    @Mapping(source = "tourLogList", target = "tourLogEntityList")
    TourEntity fromDTO(TourDTO tour);
    @Mapping(source = "tourLogEntityList", target = "tourLogList")
    TourDTO toDTO(TourEntity tourEntity);
}
