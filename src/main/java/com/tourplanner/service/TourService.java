package com.tourplanner.service;

import java.io.IOException;

public interface TourService<T> {
     void add(T tour) throws IOException;
}
