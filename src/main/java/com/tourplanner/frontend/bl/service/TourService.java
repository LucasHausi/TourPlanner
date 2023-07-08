package com.tourplanner.frontend.bl.service;

import com.tourplanner.frontend.bl.model.Tour;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TourService {
     void createOrUpdate(Tour tour) throws IOException;

     void deleteTour(UUID id) throws IOException;
     List<Tour> getAllTours() throws IOException;
    void printTourPdf(UUID id, String pdfName) throws IOException;

    void printSummaryPdf(String pdfName) throws IOException;
}
