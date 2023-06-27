package com.tourplanner.frontend.view;

import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.frontend.FXMLDependencyInjection;
import com.tourplanner.frontend.viewmodel.MainWindowViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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
    public AnchorPane tourComp;

    @FXML
    public AnchorPane tourLogComp;

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
    Button saveBtn;
    @FXML
    Label errFormField;

    @FXML
    private ReusableCompController reusableCompController;

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

        //Binding for saveBtn & errorLabel
        saveBtn.visibleProperty().bindBidirectional(mainWindowViewModel.getFormValidity());
        errFormField.visibleProperty().bind(mainWindowViewModel.getFormValidity().not());

        //load older tours from the DB
        try {
            listView.setItems(mainWindowViewModel.getTourList());
            listView.setOnMouseClicked(event -> {
                mainWindowViewModel.updateTourInfos(listView.getSelectionModel().getSelectedItem());
            });
        } catch (IOException e) {
            //gets error if the table is empty
            System.err.println(e);
        }

        ToolBar toolBar = (ToolBar) tourComp.getChildren().get(0);
        Text text = (Text)toolBar.getItems().get(0);
        text.setText("Tours");
        Button tourAdd = (Button)toolBar.getItems().get(1);
        tourAdd.setOnAction(event -> {
            try {
                addItem();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Button tourDelete = (Button)toolBar.getItems().get(2);
        tourDelete.setOnAction(event -> {
            try {
                deleteTour();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ToolBar tourLogToolBar = (ToolBar) tourLogComp.getChildren().get(0);
        Text tourLogText = (Text)tourLogToolBar.getItems().get(0);
        tourLogText.setText("TourLogs");
        Button tourLogAdd = (Button)tourLogToolBar.getItems().get(1);
        tourLogAdd.setOnAction(event -> {
            try {
                addTourLog();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
        loader.<NewTourLogController>getController().setTour(listView.getSelectionModel().getSelectedItem());
        dialog.showAndWait();

        listView.setItems(mainWindowViewModel.getTourList());
    }

    public void deleteTour() throws IOException {
        mainWindowViewModel.deleteTour(listView.getSelectionModel().getSelectedItem().getId());
        listView.setItems(mainWindowViewModel.getTourList());
    }

    public void saveChanges() throws IOException {
        this.saveBtn.visibleProperty().set(false);
        mainWindowViewModel.updateTour(listView.getSelectionModel().getSelectedItem().getId());
        listView.setItems(mainWindowViewModel.getTourList());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}