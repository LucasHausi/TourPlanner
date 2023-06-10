package com.tourplanner.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    public TextField searchField;
    @FXML
    ListView<String> listView = new ListView();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setText("Find me Nemo!");
        listView.getItems().add("Initial");
    }

    public void addItem(){
        listView.getItems().add("New");
    }
}