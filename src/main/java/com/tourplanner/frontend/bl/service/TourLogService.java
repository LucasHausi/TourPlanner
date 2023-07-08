package com.tourplanner.frontend.bl.service;

import com.tourplanner.frontend.bl.model.Tour;
import com.tourplanner.frontend.bl.model.TourLog;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TourLogService {
     void createOrUpdateTourLog(TourLog tour) throws IOException;
     void deleteTourLog(UUID tourLogId) throws IOException;
     List<TourLog> getAllTourLogsOfTour(Tour tour) throws IOException;
}
