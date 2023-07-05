package com.tourplanner.frontend;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class Main extends Application {
    private ConfigurableApplicationContext applicationContext;
    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new
                SpringApplicationBuilder(Main.class);
        builder.application()
                .setWebApplicationType(WebApplicationType.NONE);
        builder.headless(false);
        List<String> args = getParameters().getRaw(); // passed from command line
        applicationContext
                = builder.run(args.toArray(String[]::new));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.stop();
    }
    @Override
    public void start(Stage stage) throws IOException {
        //MainWindowController mainWindowController = new MainWindowController();
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 650);*/
        Parent root  = FXMLDependencyInjection.load("mainWindow.fxml", Locale.ENGLISH, applicationContext);
        Scene scene = new Scene(root);
        stage.setTitle("Tourplanner!");
        stage.setScene(scene);
        stage.show();
        //mainWindowController.setPrimaryStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}