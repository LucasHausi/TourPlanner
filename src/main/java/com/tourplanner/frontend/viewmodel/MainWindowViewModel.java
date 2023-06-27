package com.tourplanner.frontend.viewmodel;

import com.tourplanner.backend.dal.entity.TourLogEntity;
import com.tourplanner.frontend.bl.*;
import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.shared.enums.TransportType;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class MainWindowViewModel {
    TourService tourService;

    TourLogService tourLogService;

    private final SimpleStringProperty nameField = new SimpleStringProperty();
    private final SimpleStringProperty descField = new SimpleStringProperty();
    private final SimpleStringProperty fromField = new SimpleStringProperty();
    private final SimpleStringProperty toField = new SimpleStringProperty();
    private final SimpleStringProperty transTypeField = new SimpleStringProperty();
    private final SimpleStringProperty timeField = new SimpleStringProperty();
    private final SimpleStringProperty infoArea = new SimpleStringProperty();
    private final SimpleBooleanProperty formValidity = new SimpleBooleanProperty(false);
    private boolean nameErr;
    private boolean startDestEr;
    private boolean toDestErr;
    private boolean timeErr;

    public MainWindowViewModel() {

        tourService = new TourServiceImpl();
        tourLogService = new TourLogServiceImpl();
        this.nameField.addListener((observable, oldValue, newValue) -> validateName(newValue));
        this.fromField.addListener((observable, oldValue, newValue) -> validateFromDestination(newValue));
        this.toField.addListener((observable, oldValue, newValue) -> validateTODestination(newValue));
        this.timeField.addListener((observable, oldValue, newValue) -> validateTime(newValue));
    }
    private void validateName(String name) {
        boolean isValid = ValidationService.isValidName(name);
        nameErr = !isValid;
        upDateFormValidity();
    }

    private void validateFromDestination(String city) {
        boolean isValid = ValidationService.isValidDestination(city);
        startDestEr = !isValid;
        upDateFormValidity();
    }
    private void validateTODestination(String city) {
        boolean isValid = ValidationService.isValidDestination(city);
        toDestErr = !isValid;
        upDateFormValidity();
    }

    private void validateTime(String time) {
        boolean isValid = ValidationService.isValidTime(time);
        timeErr = !isValid;
        upDateFormValidity();
    }
    private void upDateFormValidity(){
        formValidity.set(!this.nameErr && !startDestEr && !toDestErr && !timeErr);
    }

    public ObservableList<TourEntity> getTourList() throws IOException {
        return tourService.getAllTours().stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
    public void deleteTour(UUID id) throws IOException {
        tourService.deleteTour(id);
    }
    public void updateTour(UUID id) throws IOException {
        TourEntity tourEntity = new TourEntity(id, nameField.get(), descField.get(), fromField.get(),
                toField.get(), TransportType.valueOf(transTypeField.get()), 0.0, timeField.get(), infoArea.get());
        tourService.createOrUpdate(tourEntity);
    }
    public void updateTourInfos(TourEntity t){
        this.nameField.set(t.getName());
        this.descField.set(t.getDescription());
        this.fromField.set(t.getStartingPoint());
        this.toField.set(t.getDestination());
        this.transTypeField.set(t.getTransportType().toString());
        this.timeField.set(t.getEstimatedTime());
        this.infoArea.set(t.getRouteInformation());

    }

    public ObservableList<TourLogEntity> getTourLogList(TourEntity tour) throws IOException {
        return tourLogService.getAllTourLogsOfTour(tour).stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
