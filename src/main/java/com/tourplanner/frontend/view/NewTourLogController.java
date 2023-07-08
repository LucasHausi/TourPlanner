package com.tourplanner.frontend.view;

import com.tourplanner.shared.enums.Difficulty;
import com.tourplanner.frontend.viewmodel.NewTourLogViewModel;
import com.tourplanner.frontend.bl.model.Tour;
import com.tourplanner.frontend.bl.model.TourLog;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
public class NewTourLogController implements Initializable {

    @FXML
    public DatePicker dateInput;
    @FXML
    public TextArea comment;
    @FXML
    public ChoiceBox<Difficulty> difficultyChoiceBox;
    @FXML
    public TextField durationInput;
    @FXML
    public Slider ratingSlider;
    @FXML
    public Button saveButton;
    @FXML
    Label errFormField;
    @FXML
    Label dateErrorLabel;
    @FXML
    Label difficultyErrorLabel;
    @FXML
    Label durationErrorLabel;

    private final NewTourLogViewModel newTourLogViewModel;
    @Setter
    Stage newTourLogDialogStage;

    public NewTourLogController(NewTourLogViewModel newTourLogViewModel){
        this.newTourLogViewModel =  newTourLogViewModel;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set initial values for the choice box;
        difficultyChoiceBox.setItems(Arrays.stream(Difficulty.values())
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));

        //Property binding
        dateInput.valueProperty().bindBidirectional(newTourLogViewModel.getDate());
        comment.textProperty().bindBidirectional(newTourLogViewModel.getComment());
        difficultyChoiceBox.valueProperty().bindBidirectional(newTourLogViewModel.getDifficulty());
        durationInput.textProperty().bindBidirectional(newTourLogViewModel.getDuration());
        ratingSlider.valueProperty().bindBidirectional(newTourLogViewModel.getRating());

        //Binding for the error labels
        errFormField.visibleProperty().bind(newTourLogViewModel.getFormValidity().not());
        dateErrorLabel.visibleProperty().bind(newTourLogViewModel.getDateValidity().not());
        difficultyErrorLabel.visibleProperty().bind(newTourLogViewModel.getDifficultyValidity().not());
        durationErrorLabel.visibleProperty().bind(newTourLogViewModel.getDurationValidity().not());

        //Binding for save button
        saveButton.visibleProperty().bind(newTourLogViewModel.getFormValidity());
    }

    public void setTour(Tour tour) {
        this.newTourLogViewModel.setTour(tour);
    }

    public void setTourLogData(TourLog tourLog){
        newTourLogViewModel.setTourLogData(tourLog);
    }

    public void saveTourLog() throws IOException{
        newTourLogViewModel.saveTourLog();
        newTourLogViewModel.clearTourLogData();
        //close dialog
        newTourLogDialogStage.close();
    }
}
