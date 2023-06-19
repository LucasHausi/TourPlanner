package com.tourplanner.frontend.viewmodel;

import com.tourplanner.backend.bl.TourService;
import com.tourplanner.backend.bl.TourServiceImpl;
import com.tourplanner.backend.dal.entity.TourEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.stream.Collectors;

public class MainWindowViewModel {
    TourService service;

    public MainWindowViewModel() {
        service = new TourServiceImpl();
    }

    public ObservableList<String> getTourList() throws IOException {
       return service.getAllTours().stream().map(TourEntity::getName).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
