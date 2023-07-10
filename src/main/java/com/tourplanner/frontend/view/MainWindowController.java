package com.tourplanner.frontend.view;

import com.tourplanner.frontend.FXMLDependencyInjection;
import com.tourplanner.frontend.bl.Subscriber;
import com.tourplanner.frontend.bl.service.TourServiceImpl;
import com.tourplanner.frontend.viewmodel.MainWindowViewModel;
import com.tourplanner.shared.enums.Difficulty;
import com.tourplanner.shared.enums.TransportType;
import com.tourplanner.frontend.bl.model.Tour;
import com.tourplanner.frontend.bl.model.TourLog;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable, Subscriber {
    @FXML
    public TextField searchField;
    @FXML
    public AnchorPane tourComp;
    @FXML
    public AnchorPane tourLogComp;

    @FXML
    ListView<Tour> listView = new ListView();
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
    TableView<TourLog> tourLogTable;

    @FXML
    TableColumn<TourLog, LocalDate> dateColumn;

    @FXML
    TableColumn<TourLog, String> durationColumn;

    @FXML
    TableColumn<TourLog, Integer> ratingColumn;

    @FXML
    TableColumn<TourLog, Difficulty> difficultyColumn;

    @FXML
    Button saveBtn;
    @FXML
    Button searchToursButton;
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
    Label popularityOvvLabel;
    @FXML
    Label childFriendlinessOvvLabel;
    @FXML
    MenuItem importBtn;
    @FXML
    MenuItem exportBtn;

    private static final Logger logger;
    static {
        try {
            // you need to do something like below instaed of Logger.getLogger(....);
            logger = LogManager.getLogger(TourServiceImpl.class);
        } catch (Throwable th) {
            throw new IllegalArgumentException("Cannot load the log property file", th);
        }
    }

    private final MainWindowViewModel mainWindowViewModel;

    public MainWindowController(MainWindowViewModel mainWindowViewModel){
        this.mainWindowViewModel =  mainWindowViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializePropertyBindings();

        try {
            listView.setItems(mainWindowViewModel.getTourList());
            listView.getSelectionModel().select(0);
            listView.setOnMouseClicked(event -> {
                Tour selectedTour = listView.getSelectionModel().getSelectedItem();
                if (selectedTour != null) {
                    mainWindowViewModel.updateEditInfos(selectedTour);
                    this.updateTourInfos(selectedTour);
                    try {
                        dateColumn.setCellValueFactory(
                                new PropertyValueFactory<>("date"));
                        durationColumn.setCellValueFactory(
                                new PropertyValueFactory<>("totalTime"));
                        ratingColumn.setCellValueFactory(
                                new PropertyValueFactory<>("rating"));
                        difficultyColumn.setCellValueFactory(
                                new PropertyValueFactory<>("difficulty"));
                        tourLogTable.setItems(mainWindowViewModel.getTourLogList(listView.getSelectionModel().getSelectedItem()));
                        tourLogTable.setOnMouseClicked(tableClickEvent -> {
                            if (tableClickEvent.getClickCount() == 2) {
                                final Stage dialog = new Stage();

                                FXMLLoader loader = FXMLDependencyInjection.getLoader("tourLogCommentWindow.fxml", Locale.ENGLISH, null);
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                loader.<TourLogCommentWindowController>getController().setTourLogCommentWindowStage(dialog);
                                TourLog selectedTourLog = tourLogTable.getSelectionModel().getSelectedItem();
                                if(selectedTourLog != null){
                                    loader.<TourLogCommentWindowController>getController().setTourLogComment(selectedTourLog.getComment());
                                }
                                Scene dialogScene = new Scene(root);
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.setTitle("TourLog Comment Display");
                                dialog.setScene(dialogScene);
                                dialog.showAndWait();
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    //Try to set the image first maybe it is in the cache
                    try {
                        routeImage.setImage(new Image("images/Route" + selectedTour.getId() + ".png"));
                    }
                    //If not found, load the image
                    catch (IllegalArgumentException ex2) {
                        try {
                            mainWindowViewModel.fetchRouteImage(selectedTour.getId(), toField.getText(), fromField.getText());
                        } catch (IOException e) {
                            //fetching image failed
                            System.err.println("Error when loading image");
                        }
                    }
                }
            });
        } catch (IOException e) {
            //gets error if the table is empty
            System.err.println(e);
        }

        //Set up our reusable component to fit its specific use cases
        setUpReusableComponentForTour();
        setUpReusableComponentForTourLog();

        searchToursButton.setOnAction(event -> {
            try {
                listView.setItems(mainWindowViewModel.getTourList().stream()
                        .filter(tour -> tour.fulltextSearch(searchField.getText()))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void addTour() throws IOException {
        final Stage dialog = new Stage();

        FXMLLoader loader = FXMLDependencyInjection.getLoader("newTour.fxml", Locale.ENGLISH, null);
        Parent root = loader.load();
        Scene dialogScene = new Scene(root);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("New Tour");
        dialog.setScene(dialogScene);
        loader.<NewTourController>getController().setNewTourDialogStage(dialog);
        dialog.showAndWait();

        listView.setItems(mainWindowViewModel.getTourList());
    }

    public void addTourLog() throws IOException {
        final Stage dialog = new Stage();

        FXMLLoader loader = FXMLDependencyInjection.getLoader("newTourLog.fxml", Locale.ENGLISH, null);
        Parent root = loader.load();
        Scene dialogScene = new Scene(root);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("New Tour Log");
        dialog.setScene(dialogScene);
        loader.<NewTourLogController>getController().setNewTourLogDialogStage(dialog);
        loader.<NewTourLogController>getController().setTour(listView.getSelectionModel().getSelectedItem());
        dialog.showAndWait();

        tourLogTable.setItems(mainWindowViewModel.getTourLogList(listView.getSelectionModel().getSelectedItem()));
    }

    public void deleteTour() throws IOException {
        Tour tour = listView.getSelectionModel().getSelectedItem();
        if(tour!=null){
            mainWindowViewModel.deleteTour(tour.getId());
        }
        listView.setItems(mainWindowViewModel.getTourList());
    }


    public void deleteTourLog() throws IOException {
        mainWindowViewModel.deleteTourLog(tourLogTable.getSelectionModel().getSelectedItem().getId());
        tourLogTable.setItems(mainWindowViewModel.getTourLogList(listView.getSelectionModel().getSelectedItem()));
    }

    public void modifyTourLog() throws IOException {
        final Stage dialog = new Stage();

        FXMLLoader loader = FXMLDependencyInjection.getLoader("newTourLog.fxml", Locale.ENGLISH, null);
        Parent root = loader.load();
        Scene dialogScene = new Scene(root);
        dialog.initModality(Modality.APPLICATION_MODAL);
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

    public void updateTourInfos(Tour t) {
        mainWindowViewModel.updateTourInfos(t);
    }

    @Override
    public void update(UUID id) {
        Tour selectedTour = listView.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            if (selectedTour.getId().equals(id)) {
                routeImage.setImage(new Image(System.getProperty("user.dir") + "/images/Route" + id + ".png"));
            }
        }
    }

    public void importFile() throws IOException {
        mainWindowViewModel.importFile();
        listView.setItems(mainWindowViewModel.getTourList());
    }

    public void exportFile() throws IOException {
        mainWindowViewModel.exportFile(this.listView.getSelectionModel().getSelectedItem());
    }

    private void initializePropertyBindings(){
        //Set initial values for the choice box;
        transTypeField.setItems(Arrays.stream(TransportType.values())
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));

        nameField.textProperty().bindBidirectional(mainWindowViewModel.getNameField());
        descField.textProperty().bindBidirectional(mainWindowViewModel.getDescField());
        fromField.textProperty().bindBidirectional(mainWindowViewModel.getFromField());
        toField.textProperty().bindBidirectional(mainWindowViewModel.getToField());
        transTypeField.valueProperty().bindBidirectional(mainWindowViewModel.getTransTypeField());
        infoArea.textProperty().bindBidirectional(mainWindowViewModel.getInfoArea());

        nameOvvLabel.textProperty().bindBidirectional(mainWindowViewModel.getNameOvvLabel());
        descOvvLabel.textProperty().bindBidirectional(mainWindowViewModel.getDescOvvLabel());
        fromOvvLabel.textProperty().bindBidirectional(mainWindowViewModel.getFromOvvLabel());
        toOvvLabel.textProperty().bindBidirectional(mainWindowViewModel.getToOvvLabel());
        distOvvLabel.textProperty().bindBidirectional(mainWindowViewModel.getDistOvvLabel());
        timeOvvLabel.textProperty().bindBidirectional(mainWindowViewModel.getTimeOvvLabel());
        infoOvvLabel.textProperty().bindBidirectional(mainWindowViewModel.getInfoOvvLabel());
        popularityOvvLabel.textProperty().bindBidirectional(mainWindowViewModel.getPopularityOvvLabel());
        childFriendlinessOvvLabel.textProperty().bindBidirectional(mainWindowViewModel.getChildFriendlinessOvvLabel());

        //Binding for saveBtn & errorLabel
        saveBtn.visibleProperty().bindBidirectional(mainWindowViewModel.getFormValidity());
        errFormField.visibleProperty().bind(mainWindowViewModel.getFormValidity().not());
    }
    private void setUpReusableComponentForTour() {
        ToolBar tourToolBar = (ToolBar) tourComp.getChildren().get(0);
        Text text = (Text) tourToolBar.getItems().get(0);
        text.setText("Tours");
        Button tourAdd = (Button) tourToolBar.getItems().get(1);
        tourAdd.setOnAction(event -> {
            try {
                addTour();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Button tourDelete = (Button) tourToolBar.getItems().get(2);
        tourDelete.setOnAction(event -> {
            try {
                deleteTour();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Button tourMisc = (Button) tourToolBar.getItems().get(3);
        tourMisc.setText("generate PDF");
        tourMisc.setOnAction(event -> {
            if(Objects.isNull(listView.getSelectionModel().getSelectedItem())){
                logger.warn("Trying to generate report without a selected tour!");
            }
            else {
                try {
                    final Stage dialog = new Stage();
                    FXMLLoader loader = FXMLDependencyInjection.getLoader("pdfGenerationDialog.fxml", Locale.ENGLISH, null);
                    Parent root = loader.load();
                    Scene dialogScene = new Scene(root);
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.setTitle("Generate Tour Pdf");
                    dialog.setScene(dialogScene);
                    loader.<PDFGenerationController>getController().setPdfGenerationDialogStage(dialog);
                    loader.<PDFGenerationController>getController().setTourToPrint(listView.getSelectionModel().getSelectedItem().getId());
                    dialog.showAndWait();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setUpReusableComponentForTourLog(){
        ToolBar tourLogToolBar = (ToolBar) tourLogComp.getChildren().get(0);
        Text tourLogText = (Text) tourLogToolBar.getItems().get(0);
        tourLogText.setText("TourLogs");
        Button tourLogAdd = (Button) tourLogToolBar.getItems().get(1);
        tourLogAdd.setOnAction(event -> {
            try {
                if(Objects.isNull(listView.getSelectionModel().getSelectedItem())){
                    logger.warn("Trying to add tour log without a selected tour!");
                }
                else {
                    addTourLog();
                    //refresh to listView
                    listView.setItems(mainWindowViewModel.getTourList());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Button tourLogDelete = (Button) tourLogToolBar.getItems().get(2);
        tourLogDelete.setOnAction(event -> {
            try {
                if(Objects.isNull(listView.getSelectionModel().getSelectedItem())){
                    logger.warn("Trying to delete tourLog without a selected tour!");
                }
                else if(Objects.isNull(tourLogTable.getSelectionModel().getSelectedItem())){
                    logger.warn("Trying to delete tourLog without a selected tourLog!");
                }
                else {
                    deleteTourLog();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Button tourLogModify = (Button) tourLogToolBar.getItems().get(3);
        tourLogModify.setText("modify");
        tourLogModify.setOnAction(event -> {
            try {
                if(Objects.isNull(listView.getSelectionModel().getSelectedItem())){
                    logger.warn("Trying to modify tourLog without a selected tour!");
                }
                else if(Objects.isNull(tourLogTable.getSelectionModel().getSelectedItem())){
                    logger.warn("Trying to modify tourLog without a selected tourLog!");
                }
                else {
                    modifyTourLog();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}