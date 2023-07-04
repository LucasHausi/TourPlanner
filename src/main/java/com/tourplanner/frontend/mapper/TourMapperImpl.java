package com.tourplanner.frontend.mapper;

import com.tourplanner.frontend.model.Tour;
import com.tourplanner.frontend.model.TourLog;
import com.tourplanner.shared.model.TourDTO;
import com.tourplanner.shared.model.TourLogDTO;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class TourMapperImpl implements TourMapper {

    @Override
    public Tour fromDTO(TourDTO tour) {
        if ( tour == null ) {
            return null;
        }

        Tour tourModel = new Tour();

        tourModel.setTourLogList( tourLogDTOListToTourLogList( tour.getTourLogList() ) );
        tourModel.setId( tour.getId() );
        tourModel.setName( tour.getName() );
        tourModel.setDescription( tour.getDescription() );
        tourModel.setStartingPoint( tour.getStartingPoint() );
        tourModel.setDestination( tour.getDestination() );
        tourModel.setTransportType( tour.getTransportType() );
        tourModel.setDistance( tour.getDistance() );
        tourModel.setEstimatedTime( tour.getEstimatedTime() );
        tourModel.setRouteInformation( tour.getRouteInformation() );

        return tourModel;
    }

    @Override
    public TourDTO toDTO(Tour tour) {
        if ( tour == null ) {
            return null;
        }

        TourDTO tourDTO = new TourDTO();

        tourDTO.setTourLogList( tourLogListToTourLogDTOList( tour.getTourLogList() ) );
        tourDTO.setId( tour.getId() );
        tourDTO.setName( tour.getName() );
        tourDTO.setDescription( tour.getDescription() );
        tourDTO.setStartingPoint( tour.getStartingPoint() );
        tourDTO.setDestination( tour.getDestination() );
        tourDTO.setTransportType( tour.getTransportType() );
        tourDTO.setDistance( tour.getDistance() );
        tourDTO.setEstimatedTime( tour.getEstimatedTime() );
        tourDTO.setRouteInformation( tour.getRouteInformation() );

        return tourDTO;
    }

    protected TourLog tourLogDTOToTourLog(TourLogDTO tourLogDTO) {
        if ( tourLogDTO == null ) {
            return null;
        }

        TourLog tourLog = new TourLog();

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

    protected List<TourLog> tourLogDTOListToTourLogList(List<TourLogDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<TourLog> list1 = new ArrayList<TourLog>( list.size() );
        for ( TourLogDTO tourLogDTO : list ) {
            list1.add( tourLogDTOToTourLog( tourLogDTO ) );
        }

        return list1;
    }

    protected TourLogDTO tourLogToTourLogDTO(TourLog tourLog) {
        if ( tourLog == null ) {
            return null;
        }

        TourLogDTO tourLogDTO = new TourLogDTO();

        tourLogDTO.setId( tourLog.getId() );
        if ( tourLog.getDate() != null ) {
            tourLogDTO.setDateTime( LocalDateTime.of(tourLog.getDate(), LocalTime.MIN).toString() );
        }
        tourLogDTO.setComment( tourLog.getComment() );
        tourLogDTO.setDifficulty( tourLog.getDifficulty() );
        tourLogDTO.setTotalTime( tourLog.getTotalTime() );
        tourLogDTO.setRating( tourLog.getRating() );

        return tourLogDTO;
    }

    protected List<TourLogDTO> tourLogListToTourLogDTOList(List<TourLog> list) {
        if ( list == null ) {
            return null;
        }

        List<TourLogDTO> list1 = new ArrayList<TourLogDTO>( list.size() );
        for ( TourLog tourLog : list ) {
            list1.add( tourLogToTourLogDTO( tourLog ) );
        }

        return list1;
    }
}
