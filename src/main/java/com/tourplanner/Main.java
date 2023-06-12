package com.tourplanner;

import com.tourplanner.view.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainWindowController mainWindowController = new MainWindowController();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 650);
        stage.setTitle("Tourplanner!");
        stage.setScene(scene);
        stage.show();
        mainWindowController.setPrimaryStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}