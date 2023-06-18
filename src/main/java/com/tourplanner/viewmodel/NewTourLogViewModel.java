package com.tourplanner.viewmodel;

import com.tourplanner.bl.model.Difficulty;
import com.tourplanner.bl.service.TourLogService;
import com.tourplanner.bl.service.TourLogServiceImpl;
import com.tourplanner.dal.entity.TourEntity;
import com.tourplanner.dal.entity.TourLogEntity;
import javafx.beans.property.*;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

//If deleted there's an error in the NewTourController.class
@Component
@Getter
public class NewTourLogViewModel {

    private final TourLogService service;
    private final ObjectProperty<LocalDateTime> date = new SimpleObjectProperty();
    private final StringProperty comment = new SimpleStringProperty();
    private final ObjectProperty<Difficulty> difficulty = new SimpleObjectProperty<>();
    private final StringProperty duration = new SimpleStringProperty();
    private final IntegerProperty rating = new SimpleIntegerProperty();


    public NewTourLogViewModel(TourLogServiceImpl service){
        this.service = service;
    }

    public void saveTourLog() throws IOException {
        TourLogEntity tourLogEntity = new TourLogEntity(UUID.randomUUID(), date.get(),comment.get(),difficulty.get(), LocalTime.parse(duration.get()),Integer.valueOf(rating.get()),new TourEntity());
        service.add(tourLogEntity);
    }
}