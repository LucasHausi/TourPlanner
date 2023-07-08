package com.tourplanner.frontend.view;

import com.tourplanner.frontend.viewmodel.TourLogCommentWindowViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ResourceBundle;

@Service
public class TourLogCommentWindowController implements Initializable {
    @FXML
    private TextArea tourLogCommentField;

    private final TourLogCommentWindowViewModel tourLogCommentWindowViewModel;

    @Setter
    private Stage tourLogCommentWindowStage;

    public TourLogCommentWindowController(TourLogCommentWindowViewModel tourLogCommentWindowViewModel){
        this.tourLogCommentWindowViewModel =  tourLogCommentWindowViewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tourLogCommentField.textProperty().bindBidirectional(tourLogCommentWindowViewModel.getTourLogComment());
    }

    public void setTourLogComment(String tourLogComment) {
        tourLogCommentWindowViewModel.setTourLogComment(tourLogComment);
    }

    public void closeCommentWindow(){
        tourLogCommentWindowStage.close();
    }
}
