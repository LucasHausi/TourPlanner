package com.tourplanner.frontend.mapper;

import com.tourplanner.frontend.model.TourLog;
import com.tourplanner.shared.model.TourLogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;


//Mappers.get(Interface)
@Mapper
public interface TourLogMapper {
    @Mapping(source = "dateTime", target = "date", qualifiedByName = "mapDateTimeToDate")
    TourLog fromDTO(TourLogDTO tourLog);
    @Mapping(source = "date", target = "dateTime")
    TourLogDTO toDTO(TourLog tourLogEntity);

    @Named("mapDateTimeToDate")
    static LocalDate mapDateTimeToDate(String date) {
        return LocalDateTime.parse(date).toLocalDate();
    }

}
