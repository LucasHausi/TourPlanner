package com.tourplanner.frontend.mapper;

import com.tourplanner.frontend.model.Tour;
import com.tourplanner.frontend.model.TourLog;
import com.tourplanner.shared.model.TourDTO;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class TourMapperImpl implements TourMapper {

    @Override
    public Tour fromDTO(TourDTO tour) {
        if ( tour == null ) {
            return null;
        }

        Tour tour1 = new Tour();

        tour1.setId( tour.getId() );
        tour1.setName( tour.getName() );
        tour1.setDescription( tour.getDescription() );
        tour1.setStartingPoint( tour.getStartingPoint() );
        tour1.setDestination( tour.getDestination() );
        tour1.setTransportType( tour.getTransportType() );
        tour1.setDistance( tour.getDistance() );
        tour1.setEstimatedTime( tour.getEstimatedTime() );
        List<TourLog> list = tour.getTourLogList();
        if ( list != null ) {
            tour1.setTourLogList( new ArrayList<TourLog>( list ) );
        }
        tour1.setRouteInformation( tour.getRouteInformation() );

        return tour1;
    }

    @Override
    public TourDTO toDTO(Tour tourEntity) {
        if ( tourEntity == null ) {
            return null;
        }

        TourDTO tourDTO = new TourDTO();

        tourDTO.setId( tourEntity.getId() );
        tourDTO.setName( tourEntity.getName() );
        tourDTO.setDescription( tourEntity.getDescription() );
        tourDTO.setStartingPoint( tourEntity.getStartingPoint() );
        tourDTO.setDestination( tourEntity.getDestination() );
        tourDTO.setTransportType( tourEntity.getTransportType() );
        tourDTO.setDistance( tourEntity.getDistance() );
        tourDTO.setEstimatedTime( tourEntity.getEstimatedTime() );
        List<TourLog> list = tourEntity.getTourLogList();
        if ( list != null ) {
            tourDTO.setTourLogList( new ArrayList<TourLog>( list ) );
        }
        tourDTO.setRouteInformation( tourEntity.getRouteInformation() );

        return tourDTO;
    }
}
