module com.tourplanner.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tourplanner.tourplanner to javafx.fxml;
    exports com.tourplanner.tourplanner;
}