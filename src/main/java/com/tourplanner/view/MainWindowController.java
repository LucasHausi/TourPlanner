package com.tourplanner.view;

import com.tourplanner.FXMLDependencyInjection;
import com.tourplanner.viewmodel.MainWindowViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    public TextField searchField;
    @FXML
    ListView<String> listView = new ListView();

    Stage primaryStage;

    private final MainWindowViewModel mainWindowViewModel;

    public MainWindowController(MainWindowViewModel mainWindowViewModel){
        this.mainWindowViewModel =  mainWindowViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addItem() throws IOException {
        final Stage dialog = new Stage();

        FXMLLoader loader =  FXMLDependencyInjection.getLoader("newTour.fxml", Locale.ENGLISH, null);
        Parent root  = loader.load();
        Scene dialogScene = new Scene(root);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("New Tour");
        dialog.setScene(dialogScene);
        loader.<NewTourController>getController().setNewTourDialogStage(dialog);
        dialog.showAndWait();

        listView.setItems(mainWindowViewModel.getTourList());
    }

    public void addTourLog() throws IOException {
        final Stage dialog = new Stage();

        FXMLLoader loader =  FXMLDependencyInjection.getLoader("newTourLog.fxml", Locale.ENGLISH, null);
        Parent root  = loader.load();
        Scene dialogScene = new Scene(root);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("New Tour Log");
        dialog.setScene(dialogScene);
        loader.<NewTourLogController>getController().setNewTourLogDialogStage(dialog);
        dialog.showAndWait();

        listView.setItems(mainWindowViewModel.getTourList());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}