package com.tourplanner.frontend.bl.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
@Service
public interface MapService {
    void getMap(UUID id, String from, String to) throws IOException;
    String[] getDistanceAndTime(String from, String to) throws IOException;
}
