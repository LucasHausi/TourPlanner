package com.tourplanner.backend.bl;

import com.tourplanner.backend.dal.entity.TourEntity;

import java.io.IOException;
import java.util.List;

public interface TourService {
     void createOrUpdate(TourEntity tour) throws IOException;

     List<TourEntity> getAllTours() throws IOException;
}
