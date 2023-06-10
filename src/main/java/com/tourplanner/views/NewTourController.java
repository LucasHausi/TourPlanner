package com.tourplanner.views;

import com.tourplanner.service.TourService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ResourceBundle;

@Service
public class NewTourController implements Initializable {

    @FXML
    public TextField nameInput;
    @FXML
    public TextField description;

    @FXML
    public TextField tourInfoInput;

    public final TourService tourService = new TourService(null);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        description.setText("This is intialized!");
    }

    public void test(String name){
        nameInput.setText(name);
    }

    public void saveTour() {
        tourInfoInput.setText("Would save the tour now!");
    }
}
