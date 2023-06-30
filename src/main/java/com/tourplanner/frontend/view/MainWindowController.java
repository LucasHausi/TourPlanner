package com.tourplanner.frontend.view;

import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.backend.dal.entity.TourLogEntity;
import com.tourplanner.frontend.FXMLDependencyInjection;
import com.tourplanner.frontend.bl.Subscriber;
import com.tourplanner.frontend.viewmodel.MainWindowViewModel;
import com.tourplanner.shared.enums.TransportType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable, Subscriber {
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
    ChoiceBox<TransportType> transTypeField;
    @FXML
    TextArea infoArea;

    @FXML
    TableView<TourLogEntity> tourLogTable;

    @FXML
    TableColumn<TourLogEntity, LocalDateTime> dateColumn;

    @FXML
    TableColumn<TourLogEntity, String> durationColumn;

    @FXML
    TableColumn<TourLogEntity, Integer> distanceColumn;

    @FXML
    Button saveBtn;
    @FXML
    Label errFormField;
    @FXML
    ImageView routeImage;
    @FXML
    Tab routeTab;
    @FXML
    Label nameOvvLabel;
    @FXML
    Label descOvvLabel;
    @FXML
    Label fromOvvLabel;
    @FXML
    Label toOvvLabel;
    @FXML
    Label distOvvLabel;
    @FXML
    Label timeOvvLabel;
    @FXML
    Label infoOvvLabel;

    @FXML
    private ReusableCompController reusableCompController;

    Stage primaryStage;

    private final MainWindowViewModel mainWindowViewModel;

    public MainWindowController(MainWindowViewModel mainWindowViewModel){
        this.mainWindowViewModel =  mainWindowViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set initial values for the choice box;
        transTypeField.setItems(Arrays.stream(TransportType.values())
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));

        nameField.textProperty().bindBidirectional(mainWindowViewModel.getNameField());
        descField.textProperty().bindBidirectional(mainWindowViewModel.getDescField());
        fromField.textProperty().bindBidirectional(mainWindowViewModel.getFromField());
        toField.textProperty().bindBidirectional(mainWindowViewModel.getToField());
        transTypeField.valueProperty().bindBidirectional(mainWindowViewModel.getTransTypeField());
        infoArea.textProperty().bindBidirectional(mainWindowViewModel.getInfoArea());

        //Binding for saveBtn & errorLabel
        saveBtn.visibleProperty().bindBidirectional(mainWindowViewModel.getFormValidity());
        errFormField.visibleProperty().bind(mainWindowViewModel.getFormValidity().not());
        //load older tours from the DB
        try {
            listView.setItems(mainWindowViewModel.getTourList());
            listView.setOnMouseClicked(event -> {
                TourEntity selectedTour = listView.getSelectionModel().getSelectedItem();
                mainWindowViewModel.updateEditInfos(selectedTour);
                this.updateTourInfos(selectedTour);

                try {
                    dateColumn.setCellValueFactory(
                            new PropertyValueFactory<TourLogEntity, LocalDateTime>("dateTime"));
                    durationColumn.setCellValueFactory(
                            new PropertyValueFactory<TourLogEntity, String>("totalTime"));
                    distanceColumn.setCellValueFactory(
                            new PropertyValueFactory<TourLogEntity, Integer>("rating"));
                    tourLogTable.setItems(mainWindowViewModel.getTourLogList(listView.getSelectionModel().getSelectedItem()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //Try to set the image first maybe it is in the cache
                try {
                    routeImage.setImage(new Image("pictures/Route"+selectedTour.getId()+".png"));
                }
                //If not found, load the image
                catch (IllegalArgumentException ex2){
                    try {
                        mainWindowViewModel.fetchRouteImage(selectedTour.getId(),toField.getText(), fromField.getText());
                    } catch (IOException e) {
                        //fetching image failed
                        System.err.println("Error when loading image");
                    }
                }
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

        Button tourLogDelete = (Button)tourLogToolBar.getItems().get(2);
        tourLogDelete.setOnAction(event -> {
            try {
                deleteTourLog();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Button tourLogModify = (Button)tourLogToolBar.getItems().get(3);
        tourLogModify.setOnAction(event -> {
            try {
                modifyTourLog();
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

        tourLogTable.setItems(mainWindowViewModel.getTourLogList(listView.getSelectionModel().getSelectedItem()));
    }

    public void deleteTour() throws IOException {
        mainWindowViewModel.deleteTour(listView.getSelectionModel().getSelectedItem().getId());
        listView.setItems(mainWindowViewModel.getTourList());
    }

    public void deleteTourLog() throws IOException {
        mainWindowViewModel.deleteTourLog(tourLogTable.getSelectionModel().getSelectedItem().getId());
        tourLogTable.setItems(mainWindowViewModel.getTourLogList(listView.getSelectionModel().getSelectedItem()));
    }

    public void modifyTourLog() throws IOException {
        final Stage dialog = new Stage();

        FXMLLoader loader =  FXMLDependencyInjection.getLoader("newTourLog.fxml", Locale.ENGLISH, null);
        Parent root  = loader.load();
        Scene dialogScene = new Scene(root);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Modify Tour Log");
        dialog.setScene(dialogScene);
        loader.<NewTourLogController>getController().setNewTourLogDialogStage(dialog);
        loader.<NewTourLogController>getController().setTour(listView.getSelectionModel().getSelectedItem());
        loader.<NewTourLogController>getController().setTourLogData(tourLogTable.getSelectionModel().getSelectedItem());
        dialog.showAndWait();

        tourLogTable.setItems(mainWindowViewModel.getTourLogList(listView.getSelectionModel().getSelectedItem()));
    }

    public void saveChanges() throws IOException {
        this.saveBtn.visibleProperty().set(false);
        mainWindowViewModel.updateTour(listView.getSelectionModel().getSelectedItem());
        listView.setItems(mainWindowViewModel.getTourList());
        nameField.clear();
        descField.clear();
        toField.clear();
        fromField.clear();
        fromField.clear();
        infoArea.clear();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void updateTourInfos(TourEntity t){
        this.nameOvvLabel.setText(t.getName());
        this.descOvvLabel.setText(t.getDescription());
        this.fromOvvLabel.setText(t.getStartingPoint());
        this.toOvvLabel.setText(t.getDestination());
        this.distOvvLabel.setText(String.valueOf(t.getDistance()));
        this.toOvvLabel.setText(t.getEstimatedTime());
        this.infoOvvLabel.setText(t.getRouteInformation());
    }

    @Override
    public void update(UUID id) {
        TourEntity selectedTour = listView.getSelectionModel().getSelectedItem();
            if(selectedTour.getId().equals(id)){
                routeImage.setImage(new Image(System.getProperty("user.dir").toString()+"/images/Route"+id+".png"));
        }
    }
}