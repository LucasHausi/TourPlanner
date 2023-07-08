package com.tourplanner.frontend.viewmodel;

import com.tourplanner.frontend.bl.service.ValidationService;
import com.tourplanner.shared.enums.Difficulty;
import com.tourplanner.frontend.bl.service.TourLogService;
import com.tourplanner.frontend.bl.service.TourLogServiceImpl;
import com.tourplanner.frontend.bl.model.Tour;
import com.tourplanner.frontend.bl.model.TourLog;
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
    private final SimpleBooleanProperty formValidity = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty dateValidity = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty durationValidity = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty difficultyValidity = new SimpleBooleanProperty(false);

    @Setter
    private Tour tour;

    @Setter
    private UUID tourLogId = null;

    public NewTourLogViewModel(TourLogServiceImpl service){
        this.service = service;
        this.date.addListener((observable, oldValue, newValue) -> validateDate(newValue));
        this.duration.addListener((observable, oldValue, newValue) -> validateDuration(newValue));
        this.difficulty.addListener((observable, oldValue, newValue) -> validateDifficulty(newValue));
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
        TourLog tourLog = new TourLog(tourLogId!=null ? tourLogId : UUID.randomUUID(), date.get(),comment.get(),difficulty.get(), duration.get(), rating.get() != 0 ? rating.get() : 1,tour);
        service.createOrUpdateTourLog(tourLog);
    }

    private void validateDate(LocalDate date) {
        boolean isValid = ValidationService.isValidDate(date);
        dateValidity.set(isValid);
        upDateFormValidity();
    }

    private void validateDuration(String duration){
        boolean isValid = ValidationService.isValidTime(duration);
        durationValidity.set(isValid);
        upDateFormValidity();
    }

    private void validateDifficulty(Difficulty difficulty){
        boolean isValid = ValidationService.isValidDifficulty(difficulty);
        difficultyValidity.set(isValid);
        upDateFormValidity();
    }

    private void upDateFormValidity(){
        formValidity.set(dateValidity.get() && durationValidity.get() && difficultyValidity.get());
    }
}
