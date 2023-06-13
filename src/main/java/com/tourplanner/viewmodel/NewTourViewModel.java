package com.tourplanner.viewmodel;

import com.tourplanner.model.Tour;
import com.tourplanner.model.TransportType;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.function.Predicate;
//If deleted there's an error in the NewTourController.class
@Repository
@Getter
public class NewTourViewModel {
    private Tour t;

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final  StringProperty from = new SimpleStringProperty();
    private final  StringProperty to = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();
    private final StringProperty tourInfo = new SimpleStringProperty();


}
