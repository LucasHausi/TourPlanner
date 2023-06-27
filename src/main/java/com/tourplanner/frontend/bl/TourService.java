package com.tourplanner.frontend.bl;

import com.tourplanner.backend.dal.entity.TourEntity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TourService {
     void createOrUpdate(TourEntity tour) throws IOException;

     void deleteTour(UUID id) throws IOException;
     List<TourEntity> getAllTours() throws IOException;
}
