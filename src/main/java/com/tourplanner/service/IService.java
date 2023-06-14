package com.tourplanner.service;

import java.io.IOException;

public interface IService<T> {
     void add(T tour) throws IOException;
}
