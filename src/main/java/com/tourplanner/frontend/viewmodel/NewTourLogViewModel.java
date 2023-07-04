package com.tourplanner.frontend.viewmodel;

import com.tourplanner.shared.enums.Difficulty;
import com.tourplanner.frontend.bl.TourLogService;
import com.tourplanner.frontend.bl.TourLogServiceImpl;
import com.tourplanner.frontend.model.Tour;
import com.tourplanner.frontend.model.TourLog;
import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

//If deleted there's an error in the NewTourController.class
@Component
@Getter
public class NewTourLogViewModel {

    private final TourLogService service;
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final StringProperty comment = new SimpleStringProperty();
    private final ObjectProperty<Difficulty> difficulty = new SimpleObjectProperty<>();
    private final StringProperty duration = new SimpleStringProperty();
    private final IntegerProperty rating = new SimpleIntegerProperty();

    @Setter
    private Tour tour;

    @Setter
    private UUID tourLogId = null;


    public NewTourLogViewModel(TourLogServiceImpl service){
        this.service = service;
    }

    public void setTourLogData(TourLog tourLog){
        this.tourLogId = tourLog.getId();
        this.date.set(tourLog.getDate());
        this.comment.set(tourLog.getComment());
        this.difficulty.set(tourLog.getDifficulty());
        this.duration.set(tourLog.getTotalTime());
        this.rating.set(tourLog.getRating());
    }
    public void clearTourLogData(){
        this.tourLogId = null;
        this.date.set(null);
        this.comment.set("");
        this.difficulty.set(null);
        this.duration.set(null);
        this.rating.set(0);
    }
    public void saveTourLog() throws IOException {
        TourLog tourLog = new TourLog(tourLogId!=null ? tourLogId : UUID.randomUUID(), date.get(),comment.get(),difficulty.get(), duration.get(),Integer.valueOf(rating.get()),tour);
        service.createOrUpdateTourLog(tourLog);
    }
}
