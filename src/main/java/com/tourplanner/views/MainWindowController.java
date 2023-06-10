package com.tourplanner.views;

import com.tourplanner.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    public TextField searchField;
    @FXML
    ListView<String> listView = new ListView();

    Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setText("Find me Nemo!");
        listView.getItems().add("Initial");
    }

    public void addItem() throws IOException {
        listView.getItems().add("New");

        final Stage dialog = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newTour.fxml"));
        Scene dialogScene = new Scene(fxmlLoader.load(), 450, 450);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("New Tour");
        dialog.setScene(dialogScene);
        dialog.show();
        fxmlLoader.<NewTourController>getController().test("I am connected");

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}