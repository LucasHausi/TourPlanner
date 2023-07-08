package com.tourplanner.frontend.viewmodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourplanner.frontend.bl.mapper.TourMapper;
import com.tourplanner.frontend.bl.service.*;
import com.tourplanner.shared.enums.TransportType;
import com.tourplanner.frontend.bl.model.Tour;
import com.tourplanner.frontend.bl.model.TourLog;
import com.tourplanner.shared.model.TourDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.mapstruct.factory.Mappers;

import javax.swing.*;
import java.io.*;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class MainWindowViewModel {
    TourService tourService;
    TourLogService tourLogService;
    MapService mapService;
    private final TourMapper tourMapper = Mappers.getMapper(TourMapper.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

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
    private final SimpleStringProperty nameOvvLabel = new SimpleStringProperty();
    private final SimpleStringProperty descOvvLabel = new SimpleStringProperty();
    private final SimpleStringProperty fromOvvLabel = new SimpleStringProperty();
    private final SimpleStringProperty toOvvLabel = new SimpleStringProperty();
    private final SimpleStringProperty distOvvLabel = new SimpleStringProperty();
    private final SimpleStringProperty timeOvvLabel = new SimpleStringProperty();
    private final SimpleStringProperty infoOvvLabel = new SimpleStringProperty();
    private final SimpleStringProperty popularityOvvLabel = new SimpleStringProperty();
    private final SimpleStringProperty childFriendlinessOvvLabel = new SimpleStringProperty();
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
        if(t!=null){
            this.nameField.set(t.getName());
            this.descField.set(t.getDescription());
            this.fromField.set(t.getStartingPoint());
            this.toField.set(t.getDestination());
            this.transTypeField.set(t.getTransportType());
            this.infoArea.set(t.getRouteInformation());
        }

    }
    public void updateTourInfos(Tour t){
        if(t!=null){
            this.nameOvvLabel.set(t.getName());
            this.descOvvLabel.set(t.getDescription());
            this.fromOvvLabel.set(t.getStartingPoint());
            this.toOvvLabel.set(t.getDestination());
            this.distOvvLabel.set(t.getDistance()+"km");
            int sec = Integer.valueOf(t.getEstimatedTime());
            Duration duration = Duration.ofSeconds(sec);
            long HH = duration.toHours();
            long MM = duration.toMinutesPart();
            long SS = duration.toSecondsPart();
            String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
            this.timeOvvLabel.set(timeInHHMMSS);
            this.infoOvvLabel.set(t.getRouteInformation());
            this.popularityOvvLabel.set(t.getPopularity().label);
            this.childFriendlinessOvvLabel.set(t.getChildFriendliness().label);
        }
    }


    public void fetchRouteImage(UUID id, String to, String from) throws IOException {
        mapService.getMap(id, to, from);
    }


    public ObservableList<TourLog> getTourLogList(Tour tour) throws IOException {
        if(tour!=null){
            return tourLogService.getAllTourLogsOfTour(tour).stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        return null;
    }
    public void exportFile(Tour tour) throws IOException {
        JFileChooser chooser = new JFileChooser();
        TourDTO tourDTO = tourMapper.toDTO(tour);
        int returnVal = chooser.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println(chooser.getSelectedFile().getAbsolutePath());
            objectMapper.writeValue(new File(chooser.getSelectedFile().getAbsolutePath()+".json"),tourDTO);
        }
    }
    public void importFile() throws IOException, ClassNotFoundException {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(chooser.getSelectedFile().getAbsolutePath()));
            String serializedTour = bufferedReader.readLine();
            TourDTO tourDTO = new ObjectMapper().readValue(serializedTour,TourDTO.class);
            Tour tour = tourMapper.fromDTO(tourDTO);
            List<TourLog> tempTourLogs = tour.getTourLogList();
            tour.setTourLogList(null);
            UUID id = tourService.createOrUpdate(tour);
            tour.setId(id);
            for(TourLog tourLog : tempTourLogs)
            {
                tourLog.setTour(tour);
                tourLogService.createOrUpdateTourLog(tourLog);
            }
            bufferedReader.close();
        }
    }
}
