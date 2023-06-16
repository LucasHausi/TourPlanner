package com.tourplanner.viewmodel;

import com.tourplanner.bl.service.TourService;
import com.tourplanner.bl.service.TourServiceImpl;
import com.tourplanner.dal.entity.TourEntity;
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
