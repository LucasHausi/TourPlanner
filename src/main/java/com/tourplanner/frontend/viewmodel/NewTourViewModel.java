package com.tourplanner.frontend.viewmodel;

import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.frontend.bl.ValidationService;
import com.tourplanner.shared.enums.TransportType;
import com.tourplanner.frontend.bl.TourService;
import com.tourplanner.frontend.bl.TourServiceImpl;

import javafx.beans.property.*;
import lombok.Getter;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.UUID;
//If deleted there's an error in the NewTourController.class
@Component
@Getter
public class NewTourViewModel {

    private final TourService service;
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
    private final  BooleanProperty timeErrorVisible = new SimpleBooleanProperty(false);
    private final  BooleanProperty formValidity= new SimpleBooleanProperty(false);


    public NewTourViewModel(TourServiceImpl service){
        this.service = service;
        this.name.addListener((observable, oldValue, newValue) -> validateName(newValue));
        this.from.addListener((observable, oldValue, newValue) -> validateFromDestination(newValue));
        this.to.addListener((observable, oldValue, newValue) -> validateTODestination(newValue));
        this.time.addListener((observable, oldValue, newValue) -> validateTime(newValue));
    }

    private void validateName(String name) {
        boolean isValid = ValidationService.isValidName(name);
        nameErrorVisible.set(!isValid);
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

    private void validateTime(String time) {
        boolean isValid = ValidationService.isValidTime(time);
        timeErrorVisible.set(!isValid);
        upDateFormValidity();
    }
    private void upDateFormValidity(){
        formValidity.set(!nameErrorVisible.get() && !startDestinationErrorVisible.get() && !endDestinationErrorVisible.get() && !timeErrorVisible.get());
    }

    public void saveTour() throws IOException {
        TourEntity tourEntity = new TourEntity(UUID.randomUUID(), name.get(), description.get(), from.get(),
                        to.get(), transportType.get(), 0.0, time.get(), tourInfo.get());
        service.createOrUpdate(tourEntity);
    }
}
