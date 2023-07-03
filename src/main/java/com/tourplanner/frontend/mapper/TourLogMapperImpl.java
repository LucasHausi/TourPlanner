package com.tourplanner.frontend.mapper;

import com.tourplanner.frontend.model.TourLog;
import com.tourplanner.shared.model.TourLogDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TourLogMapperImpl implements TourLogMapper {

    @Override
    public TourLog fromDTO(TourLogDTO tourLog) {
        if ( tourLog == null ) {
            return null;
        }

        TourLog tourLog1 = new TourLog();

        tourLog1.setId( tourLog.getId() );
        tourLog1.setDateTime( tourLog.getDateTime() );
        tourLog1.setComment( tourLog.getComment() );
        tourLog1.setDifficulty( tourLog.getDifficulty() );
        tourLog1.setTotalTime( tourLog.getTotalTime() );
        tourLog1.setRating( tourLog.getRating() );
        tourLog1.setTour( tourLog.getTour() );

        return tourLog1;
    }

    @Override
    public TourLogDTO toDTO(TourLog tourLogEntity) {
        if ( tourLogEntity == null ) {
            return null;
        }

        TourLogDTO tourLogDTO = new TourLogDTO();

        tourLogDTO.setId( tourLogEntity.getId() );
        tourLogDTO.setDateTime( tourLogEntity.getDateTime() );
        tourLogDTO.setComment( tourLogEntity.getComment() );
        tourLogDTO.setDifficulty( tourLogEntity.getDifficulty() );
        tourLogDTO.setTotalTime( tourLogEntity.getTotalTime() );
        tourLogDTO.setRating( tourLogEntity.getRating() );
        tourLogDTO.setTour( tourLogEntity.getTour() );

        return tourLogDTO;
    }
}
