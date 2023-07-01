package com.tourplanner.frontend.viewmodel;

import com.tourplanner.frontend.bl.*;
import com.tourplanner.backend.dal.entity.TourLogEntity;
import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.shared.enums.TransportType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    MapService mapService;

    private final SimpleStringProperty nameField = new SimpleStringProperty();
    private final SimpleStringProperty descField = new SimpleStringProperty();
    private final SimpleStringProperty fromField = new SimpleStringProperty();
    private final SimpleStringProperty toField = new SimpleStringProperty();
    private final ObjectProperty<TransportType> transTypeField = new SimpleObjectProperty<>();
    private final SimpleStringProperty infoArea = new SimpleStringProperty();
    private final SimpleBooleanProperty formValidity = new SimpleBooleanProperty(false);
    private boolean nameErr;
    private boolean startDestEr;
    private boolean toDestErr;
    private boolean transTypErr;

    public MainWindowViewModel() {

        mapService = new MapServiceImp();
        tourService = new TourServiceImpl();
        tourLogService = new TourLogServiceImpl();
        this.nameField.addListener((observable, oldValue, newValue) -> validateName(newValue));
        this.fromField.addListener((observable, oldValue, newValue) -> validateFromDestination(newValue));
        this.toField.addListener((observable, oldValue, newValue) -> validateTODestination(newValue));
        this.transTypeField.addListener((observable, oldValue, newValue) -> validateTransType(newValue));
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

    private void validateTransType(TransportType transportType){
        boolean isValid = ValidationService.isValidTransType(transportType);
        transTypErr = !isValid;
        upDateFormValidity();
    }
    private void upDateFormValidity(){
        formValidity.set(!this.nameErr && !startDestEr && !toDestErr && !transTypErr);
    }

    public ObservableList<TourEntity> getTourList() throws IOException {
        return tourService.getAllTours().stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
    public void deleteTour(UUID id) throws IOException {
        tourService.deleteTour(id);
    }

    public void printTourPdf(UUID id) throws IOException {
        tourService.printTourPdf(id);
    }

    public void deleteTourLog(UUID tourLogId) throws IOException {
        tourLogService.deleteTourLog(tourLogId);
    }
    public void updateTour(TourEntity t) throws IOException {
        String[] distAndTime = mapService.getDistanceAndTime(t.getStartingPoint(),t.getDestination());
        TourEntity tourEntity = new TourEntity(t.getId(), nameField.get(), descField.get(), fromField.get(),
                toField.get(), transTypeField.get(),Double.parseDouble(distAndTime[0]), distAndTime[1],t.getTourLogEntityList(), infoArea.get());
        tourService.createOrUpdate(tourEntity);
    }
    public void updateEditInfos(TourEntity t){
        this.nameField.set(t.getName());
        this.descField.set(t.getDescription());
        this.fromField.set(t.getStartingPoint());
        this.toField.set(t.getDestination());
        this.transTypeField.set(t.getTransportType());
        this.infoArea.set(t.getRouteInformation());
    }


    public void fetchRouteImage(UUID id, String to, String from) throws IOException {
        mapService.getMap(id, to, from);
    }


    public ObservableList<TourLogEntity> getTourLogList(TourEntity tour) throws IOException {
        return tourLogService.getAllTourLogsOfTour(tour).stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
