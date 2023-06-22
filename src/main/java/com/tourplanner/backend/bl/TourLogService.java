package com.tourplanner.backend.bl;

import com.tourplanner.backend.dal.entity.TourLogEntity;

import java.io.IOException;
import java.util.List;

public interface TourLogService {
     void createOrUpdateTourLog(TourLogEntity tour) throws IOException;

     List<TourLogEntity> getAllTours() throws IOException;
}
