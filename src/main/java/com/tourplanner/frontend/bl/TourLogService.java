package com.tourplanner.frontend.bl;

import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.backend.dal.entity.TourLogEntity;

import java.io.IOException;
import java.util.List;

public interface TourLogService {
     void createOrUpdateTourLog(TourLogEntity tour) throws IOException;

     List<TourLogEntity> getAllTours() throws IOException;

     List<TourLogEntity> getAllTourLogsOfTour(TourEntity tour) throws IOException;
}