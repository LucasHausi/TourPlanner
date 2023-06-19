package com.tourplanner.backend.bl;

import java.io.IOException;

public interface MapService {
    String getMap(String from, String to) throws IOException;

}
