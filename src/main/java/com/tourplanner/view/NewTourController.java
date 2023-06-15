package com.tourplanner.view;

import com.tourplanner.model.TransportType;
import com.tourplanner.viewmodel.NewTourViewModel;
import com.tourplanner.repository.TourApi;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

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
    Retrofit retrofit;
    TourApi service;

    @Setter
    Stage newTourDialogStage;

    public NewTourController(NewTourViewModel newTourViewModel){
        this.newTourViewModel =  newTourViewModel;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30019")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(TourApi.class);
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
