package com.tourplanner.frontend.view;

import com.tourplanner.shared.enums.Difficulty;
import com.tourplanner.frontend.viewmodel.NewTourLogViewModel;
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
    public TextField commentInput;
    @FXML
    public ChoiceBox<Difficulty> difficultyChoiceBox;
    @FXML
    public TextField durationInput;
    @FXML
    public Slider ratingSlider;

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
        //dateInput.valueProperty().bindBidirectional(newTourLogViewModel.getDate());
        commentInput.textProperty().bindBidirectional(newTourLogViewModel.getComment());
        difficultyChoiceBox.valueProperty().bindBidirectional(newTourLogViewModel.getDifficulty());
        durationInput.textProperty().bindBidirectional(newTourLogViewModel.getDuration());
        ratingSlider.valueProperty().bindBidirectional(newTourLogViewModel.getRating());
    }
    public void saveTourLog() throws IOException{
        newTourLogViewModel.saveTourLog();
        //close dialog
        newTourLogDialogStage.close();
    }
}
