package com.tourplanner.frontend.viewmodel;

import com.tourplanner.frontend.bl.service.*;
import com.tourplanner.shared.enums.TransportType;
import com.tourplanner.frontend.bl.model.Tour;
import com.tourplanner.frontend.bl.model.TourLog;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import javax.swing.*;
import java.io.*;
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

        mapService = new MapServiceImpl();
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

    public ObservableList<Tour> getTourList() throws IOException {
        return tourService.getAllTours().stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
    public void deleteTour(UUID id) throws IOException {
        tourService.deleteTour(id);
    }



    public void deleteTourLog(UUID tourLogId) throws IOException {
        tourLogService.deleteTourLog(tourLogId);
    }
    public void updateTour(Tour t) throws IOException {
        String[] distAndTime = mapService.getDistanceAndTime(t.getStartingPoint(),t.getDestination());
        Tour tour = new Tour(t.getId(), nameField.get(), descField.get(), fromField.get(),
                toField.get(), transTypeField.get(),Double.parseDouble(distAndTime[0]), distAndTime[1],t.getTourLogList(), infoArea.get());
        tourService.createOrUpdate(tour);
    }
    public void updateEditInfos(Tour t){
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


    public ObservableList<TourLog> getTourLogList(Tour tour) throws IOException {
        return tourLogService.getAllTourLogsOfTour(tour).stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
    public void exportFile(Tour t) throws IOException {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            FileOutputStream fileOutputStream = new FileOutputStream(chooser.getSelectedFile().getAbsolutePath());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(t);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
    }
    public void importFile() throws IOException, ClassNotFoundException {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            FileInputStream fileInputStream = new FileInputStream(chooser.getSelectedFile().getAbsolutePath());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Tour t = (Tour) objectInputStream.readObject();
            tourService.createOrUpdate(t);
            objectInputStream.close();
        }
    }
}
