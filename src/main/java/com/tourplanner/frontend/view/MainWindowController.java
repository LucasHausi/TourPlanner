package com.tourplanner.frontend.view;

import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.frontend.FXMLDependencyInjection;
import com.tourplanner.frontend.viewmodel.MainWindowViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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
    ListView<TourEntity> listView = new ListView();
    @FXML
    TextField nameField;
    @FXML
    TextField descField;
    @FXML
    TextField fromField;
    @FXML
    TextField toField;
    @FXML
    TextField transTypeField;
    @FXML
    TextField timeField;
    @FXML
    TextArea infoArea;
    @FXML
    Button editBtn;
    @FXML
    Button saveBtn;

    Stage primaryStage;

    private final MainWindowViewModel mainWindowViewModel;

    public MainWindowController(MainWindowViewModel mainWindowViewModel){
        this.mainWindowViewModel =  mainWindowViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameField.textProperty().bindBidirectional(mainWindowViewModel.getNameField());
        descField.textProperty().bindBidirectional(mainWindowViewModel.getDescField());
        fromField.textProperty().bindBidirectional(mainWindowViewModel.getFromField());
        toField.textProperty().bindBidirectional(mainWindowViewModel.getToField());
        transTypeField.textProperty().bindBidirectional(mainWindowViewModel.getTransTypeField());
        timeField.textProperty().bindBidirectional(mainWindowViewModel.getTimeField());
        infoArea.textProperty().bindBidirectional(mainWindowViewModel.getInfoArea());

        //load older tours from the DB
        try {
            listView.setItems(mainWindowViewModel.getTourList());
        } catch (IOException e) {
            //gets error if the table is empty
        }
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
        listView.setOnMouseClicked(event -> {
            mainWindowViewModel.updateTourInfos(listView.getSelectionModel().getSelectedItem());
        });
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
        loader.<NewTourLogController>getController().setTour(listView.getSelectionModel().getSelectedItem());
        dialog.showAndWait();

        listView.setItems(mainWindowViewModel.getTourList());
    }

    public void deleteTour(){

    }

    public void activateEditing(){
        this.nameField.editableProperty().set(true);
        this.descField.editableProperty().set(true);
        this.fromField.editableProperty().set(true);
        this.toField.editableProperty().set(true);
        this.transTypeField.editableProperty().set(true);
        this.timeField.editableProperty().set(true);
        this.infoArea.editableProperty().set(true);
        this.saveBtn.visibleProperty().set(true);
    }
    public void saveChanges() throws IOException {
        this.nameField.editableProperty().set(false);
        this.descField.editableProperty().set(false);
        this.fromField.editableProperty().set(false);
        this.toField.editableProperty().set(false);
        this.transTypeField.editableProperty().set(false);
        this.timeField.editableProperty().set(false);
        this.infoArea.editableProperty().set(false);
        this.saveBtn.visibleProperty().set(false);
        mainWindowViewModel.updateTour();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}