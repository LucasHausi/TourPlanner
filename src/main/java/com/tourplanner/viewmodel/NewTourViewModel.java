package com.tourplanner.viewmodel;

import com.tourplanner.dal.entity.TourEntity;
import com.tourplanner.bl.model.TransportType;
import com.tourplanner.bl.service.TourService;
import com.tourplanner.bl.service.TourServiceImpl;
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

    public NewTourViewModel(TourServiceImpl service){
        this.service = service;
    }

    public void saveTour() throws IOException {
        TourEntity tourEntity = new TourEntity(UUID.randomUUID(), name.get(), description.get(), from.get(),
                        to.get(), transportType.get(), 0.0, time.get(), tourInfo.get());
        service.add(tourEntity);
    }
}
