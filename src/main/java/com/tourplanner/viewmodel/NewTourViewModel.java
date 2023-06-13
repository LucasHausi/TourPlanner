package com.tourplanner.viewmodel;

import com.tourplanner.model.Tour;
import com.tourplanner.model.TransportType;
import com.tourplanner.service.TourService;
import com.tourplanner.web.ControllerService;
import javafx.beans.property.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Predicate;
//If deleted there's an error in the NewTourController.class
@Component
@Getter
public class NewTourViewModel {

    private final TourService service;
    private Tour t;

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final  StringProperty from = new SimpleStringProperty();
    private final  StringProperty to = new SimpleStringProperty();
    private final ObjectProperty<TransportType> transportType = new SimpleObjectProperty<>();
    private final StringProperty time = new SimpleStringProperty();
    private final StringProperty tourInfo = new SimpleStringProperty();

    public NewTourViewModel(TourService service){
        this.service = service;
    }

    public void saveTour() throws IOException {
        /*service.newTour(new Tour(UUID.randomUUID(), name.get(), description.get(), from.get(),
                        to.get(), transportType.get(), 0.0, time.get(), tourInfo.get()))
                .execute();*/
    }
}
