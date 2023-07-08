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
public class TourPlannerFXApp extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        SpringApplicationBuilder builder = new
                SpringApplicationBuilder(TourPlannerFXApp.class);
        builder.application()
                .setWebApplicationType(WebApplicationType.NONE);
        builder.headless(false);
        List<String> args = getParameters().getRaw(); // passed from command line
        applicationContext = builder.run(args.toArray(String[]::new));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.stop();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root  = FXMLDependencyInjection.load("mainWindow.fxml", Locale.ENGLISH, applicationContext);
        Scene scene = new Scene(root);
        stage.setTitle("Tourplanner!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}