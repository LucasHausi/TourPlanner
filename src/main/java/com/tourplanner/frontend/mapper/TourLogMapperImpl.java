package com.tourplanner.frontend.mapper;

import com.tourplanner.frontend.model.Tour;
import com.tourplanner.frontend.model.TourLog;
import com.tourplanner.shared.model.TourDTO;
import com.tourplanner.shared.model.TourLogDTO;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
public class TourLogMapperImpl implements TourLogMapper {

    TourMapper tourMapper = new TourMapperImpl();

    @Override
    public TourLog fromDTO(TourLogDTO tourLogDTO) {
        if ( tourLogDTO == null ) {
            return null;
        }

        TourLog tourLog = new TourLog();

        tourLog.setTour( tourDTOToTour( tourLogDTO.getTour() ) );
        tourLog.setId( tourLogDTO.getId() );
        if ( tourLogDTO.getDateTime() != null ) {
            tourLog.setDate( LocalDateTime.parse( tourLogDTO.getDateTime() ).toLocalDate() );
        }
        tourLog.setComment( tourLogDTO.getComment() );
        tourLog.setDifficulty( tourLogDTO.getDifficulty() );
        tourLog.setTotalTime( tourLogDTO.getTotalTime() );
        tourLog.setRating( tourLogDTO.getRating() );

        return tourLog;
    }

    @Override
    public TourLogDTO toDTO(TourLog tourLog) {
        if ( tourLog == null ) {
            return null;
        }

        TourLogDTO tourLogDTO = new TourLogDTO();

        tourLogDTO.setId( tourLog.getId() );
        tourLogDTO.setDateTime( LocalDateTime.of(tourLog.getDate(), LocalTime.MIN).toString() );
        tourLogDTO.setComment( tourLog.getComment() );
        tourLogDTO.setDifficulty( tourLog.getDifficulty() );
        tourLogDTO.setTotalTime( tourLog.getTotalTime() );
        tourLogDTO.setRating( tourLog.getRating() );
        tourLogDTO.setTour(tourMapper.toDTO(tourLog.getTour()));

        return tourLogDTO;
    }

    protected Tour tourDTOToTour(TourDTO tourDTO) {
        if ( tourDTO == null ) {
            return null;
        }

        Tour tour = new Tour();

        tour.setId( tourDTO.getId() );
        tour.setName( tourDTO.getName() );
        tour.setDescription( tourDTO.getDescription() );
        tour.setStartingPoint( tourDTO.getStartingPoint() );
        tour.setDestination( tourDTO.getDestination() );
        tour.setTransportType( tourDTO.getTransportType() );
        tour.setDistance( tourDTO.getDistance() );
        tour.setEstimatedTime( tourDTO.getEstimatedTime() );
        tour.setRouteInformation( tourDTO.getRouteInformation() );

        return tour;
    }
}
