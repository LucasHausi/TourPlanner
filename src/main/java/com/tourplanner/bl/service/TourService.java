package com.tourplanner.bl.service;

import com.tourplanner.dal.entity.TourEntity;

import java.io.IOException;
import java.util.List;

public interface TourService {
     void add(TourEntity tour) throws IOException;

     List<TourEntity> getAllTours() throws IOException;
}
