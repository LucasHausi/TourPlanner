package com.tourplanner.frontend.view;

import com.tourplanner.frontend.viewmodel.ReusableCompViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ResourceBundle;

@Service
public class ReusableCompController implements Initializable {
    @FXML
    Text buttonGroupText;
    @FXML
    Button add;
    @FXML
    Button delete;
    @FXML
    Button misc;

    private final ReusableCompViewModel reusableCompViewModel;

    public ReusableCompController(ReusableCompViewModel reusableCompViewModel) {
        this.reusableCompViewModel = reusableCompViewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonGroupText.setText("Tour");
    }

    public void add(){

    }
}
