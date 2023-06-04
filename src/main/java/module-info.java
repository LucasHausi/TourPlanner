module com.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.persistence;
    requires spring.context;
    requires spring.data.jpa;


    opens com.tourplanner to javafx.fxml;
    exports com.tourplanner;
}