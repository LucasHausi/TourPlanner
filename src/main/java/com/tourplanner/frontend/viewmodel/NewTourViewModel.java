package com.tourplanner.frontend.viewmodel;

import com.tourplanner.frontend.bl.MapService;
import com.tourplanner.frontend.bl.ValidationService;
import com.tourplanner.shared.enums.TransportType;
import com.tourplanner.frontend.bl.TourService;
import com.tourplanner.frontend.bl.TourServiceImpl;

import com.tourplanner.frontend.model.Tour;
import javafx.beans.property.*;
import lombok.Getter;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
//If deleted there's an error in the NewTourController.class
@Component
@Getter
public class NewTourViewModel {

    private final TourService tourService;
    private final MapService mapService;

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final  StringProperty from = new SimpleStringProperty();
    private final  StringProperty to = new SimpleStringProperty();
    private final ObjectProperty<TransportType> transportType = new SimpleObjectProperty<>();
    private final StringProperty time = new SimpleStringProperty();
    private final StringProperty tourInfo = new SimpleStringProperty();
    private final  BooleanProperty nameErrorVisible = new SimpleBooleanProperty(false);
    private final  BooleanProperty startDestinationErrorVisible = new SimpleBooleanProperty(false);
    private final  BooleanProperty endDestinationErrorVisible = new SimpleBooleanProperty(false);
    private final  BooleanProperty formValidity= new SimpleBooleanProperty(false);
    private final BooleanProperty transportTypeErrorVisible = new SimpleBooleanProperty(true);


    public NewTourViewModel(TourServiceImpl tourService, MapService mapService){
        this.tourService = tourService;
        this.mapService = mapService;
        this.name.addListener((observable, oldValue, newValue) -> validateName(newValue));
        this.from.addListener((observable, oldValue, newValue) -> validateFromDestination(newValue));
        this.to.addListener((observable, oldValue, newValue) -> validateTODestination(newValue));
        this.transportType.addListener((observable, oldValue, newValue) -> validateTransType(newValue));
    }

    private void validateName(String name) {
        boolean isValid = ValidationService.isValidName(name);
        nameErrorVisible.set(!isValid);
        upDateFormValidity();
    }
    private void validateTransType(TransportType transportType){
        boolean isValid = ValidationService.isValidTransType(transportType);
        transportTypeErrorVisible.set(!isValid);
        upDateFormValidity();
    }

    private void validateFromDestination(String city) {
        boolean isValid = ValidationService.isValidDestination(city);
        startDestinationErrorVisible.set(!isValid);
        upDateFormValidity();
    }
    private void validateTODestination(String city) {
        boolean isValid = ValidationService.isValidDestination(city);
        endDestinationErrorVisible.set(!isValid);
        upDateFormValidity();
    }

    private void upDateFormValidity(){
        formValidity.set(!nameErrorVisible.get() && !startDestinationErrorVisible.get() && !endDestinationErrorVisible.get()&& !transportTypeErrorVisible.get());
    }
    private String[] fetchDistanceAndTime(String from, String to) throws IOException {
        return mapService.getDistanceAndTime(from,to);
    }

    public void saveTour() throws IOException {
        String[] distAndTime = fetchDistanceAndTime(from.get(),to.get());

        Tour tour = new Tour(UUID.randomUUID(), name.get(), description.get(), from.get(),
                        to.get(), transportType.get(), Double.parseDouble(distAndTime[0]), distAndTime[1], new ArrayList<>(),tourInfo.get());
        tourService.createOrUpdate(tour);
    }
}
