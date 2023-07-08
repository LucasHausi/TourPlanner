package com.tourplanner.frontend.view;

import com.tourplanner.shared.enums.TransportType;
import com.tourplanner.frontend.viewmodel.NewTourViewModel;
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
public class NewTourController implements Initializable {

    @FXML
    public TextField nameInput;
    @FXML
    public TextArea description;

    @FXML
    public TextField fromInput;

    @FXML
    public TextField toInput;

    @FXML
    public ChoiceBox<TransportType> transportTypeInput;
    @FXML
    public TextArea tourInfoInput;
    @FXML
    public Label nameErrLabel;
    @FXML
    public Label fromErrLabel;
    @FXML
    public Label toErrLabel;
    @FXML
    public Label transTypeErrLabel;
    @FXML
    public Button saveButton;

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
        description.textProperty().bindBidirectional(newTourViewModel.getDescription());
        toInput.textProperty().bindBidirectional(newTourViewModel.getTo());
        fromInput.textProperty().bindBidirectional(newTourViewModel.getFrom());
        transportTypeInput.valueProperty().bindBidirectional(newTourViewModel.getTransportType());
        tourInfoInput.textProperty().bindBidirectional(newTourViewModel.getTourInfo());

        //Binding for the error labels
        nameErrLabel.visibleProperty().bind(newTourViewModel.getNameErrorVisible());
        fromErrLabel.visibleProperty().bind(newTourViewModel.getStartDestinationErrorVisible());
        toErrLabel.visibleProperty().bind(newTourViewModel.getEndDestinationErrorVisible());
        transTypeErrLabel.visibleProperty().bind(newTourViewModel.getTransportTypeErrorVisible());

        //Binding for save button
        saveButton.visibleProperty().bind(newTourViewModel.getFormValidity());

    }

    public void saveTour() throws IOException{
        newTourViewModel.saveTour();
        clearInputFields();
        //close dialog
        newTourDialogStage.close();
    }

    public void clearInputFields(){
        nameInput.clear();
        description.clear();
        toInput.clear();
        fromInput.clear();
        transportTypeInput.setValue(null);
        tourInfoInput.clear();
    }
}
