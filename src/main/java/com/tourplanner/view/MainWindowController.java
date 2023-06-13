package com.tourplanner.view;

import com.tourplanner.FXMLDependencyInjection;
import com.tourplanner.Main;
import com.tourplanner.model.Tour;
import com.tourplanner.viewmodel.NewTourViewModel;
import com.tourplanner.web.ControllerService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {
    @FXML
    public TextField searchField;
    @FXML
    ListView<String> listView = new ListView();

    Stage primaryStage;

    Retrofit retrofit;
    ControllerService service;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30019")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(ControllerService.class);
    }

    public void addItem() throws IOException {
        final Stage dialog = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newTour.fxml"));
        fxmlLoader.setControllerFactory(e -> new NewTourController(new NewTourViewModel()));
        Scene dialogScene = new Scene(fxmlLoader.load(), 450, 450);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("New Tour");
        dialog.setScene(dialogScene);
        fxmlLoader.<NewTourController>getController().setNewTourDialogStage(dialog);
        dialog.showAndWait();
        listView.setItems(service.getAllTours().execute().body().stream().map(Tour::getName).collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}