package com.tourplanner.bl.service;

import com.tourplanner.dal.entity.TourLogEntity;

import java.io.IOException;
import java.util.List;

public interface TourLogService {
     void add(TourLogEntity tour) throws IOException;

     List<TourLogEntity> getAllTours() throws IOException;
}
