package com.tourplanner.frontend.bl;

import javafx.util.Pair;

import java.io.IOException;
import java.util.UUID;

public interface MapService {
    void getMap(UUID id, String from, String to) throws IOException;
    String[] getDistanceAndTime(String from, String to) throws IOException;
}
