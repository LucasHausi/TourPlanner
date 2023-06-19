package com.tourplanner.frontend.view;

import com.tourplanner.shared.enums.TransportType;
import com.tourplanner.frontend.viewmodel.NewTourViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
public class NewTourController implements Initializable {

    @FXML
    public TextField nameInput;
    @FXML
    public TextField descriptionInput;

    @FXML
    public TextField fromInput;

    @FXML
    public TextField toInput;

    @FXML
    public ChoiceBox<TransportType> transportTypeInput;

    @FXML
    public TextField timeInput;
    @FXML
    public TextArea tourInfoInput;

    private final NewTourViewModel newTourViewModel;

    @Setter
    Stage newTourDialogStage;

    public NewTourController(NewTourViewModel newTourViewModel){
        this.newTourViewModel =  newTourViewModel;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set initial values for the choice box;
        transportTypeInput.setItems(Arrays.stream(TransportType.values())
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));

        //Property binding
        nameInput.textProperty().bindBidirectional(newTourViewModel.getName());
        descriptionInput.textProperty().bindBidirectional(newTourViewModel.getDescription());
        toInput.textProperty().bindBidirectional(newTourViewModel.getTo());
        fromInput.textProperty().bindBidirectional(newTourViewModel.getFrom());
        transportTypeInput.valueProperty().bindBidirectional(newTourViewModel.getTransportType());
        timeInput.textProperty().bindBidirectional(newTourViewModel.getTime());
        tourInfoInput.textProperty().bindBidirectional(newTourViewModel.getTourInfo());
    }
    public void saveTour() throws IOException{
        newTourViewModel.saveTour();
        //close dialog
        newTourDialogStage.close();
    }
}
