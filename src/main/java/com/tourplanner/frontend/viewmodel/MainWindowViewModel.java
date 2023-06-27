package com.tourplanner.frontend.viewmodel;

import com.tourplanner.frontend.bl.TourService;
import com.tourplanner.frontend.bl.TourServiceImpl;
import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.shared.enums.TransportType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class MainWindowViewModel {
    TourService service;

    private final SimpleStringProperty nameField = new SimpleStringProperty();
    private final SimpleStringProperty descField = new SimpleStringProperty();
    private final SimpleStringProperty fromField = new SimpleStringProperty();
    private final SimpleStringProperty toField = new SimpleStringProperty();
    private final SimpleStringProperty transTypeField = new SimpleStringProperty();
    private final SimpleStringProperty timeField = new SimpleStringProperty();
    private final SimpleStringProperty infoArea = new SimpleStringProperty();

    public MainWindowViewModel() {
        service = new TourServiceImpl();
    }

    public ObservableList<TourEntity> getTourList() throws IOException {
        return service.getAllTours().stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
    public void deleteTour(UUID id) throws IOException {
        service.deleteTour(id);
    }
    public void updateTour(UUID id) throws IOException {
        TourEntity tourEntity = new TourEntity(id, nameField.get(), descField.get(), fromField.get(),
                toField.get(), TransportType.valueOf(transTypeField.get()), 0.0, timeField.get(), infoArea.get());
        service.createOrUpdate(tourEntity);
    }
    public void updateTourInfos(TourEntity t){
        this.nameField.set(t.getName());
        this.descField.set(t.getDescription());
        this.fromField.set(t.getStartingPoint());
        this.toField.set(t.getDestination());
        this.transTypeField.set(t.getTransportType().toString());
        this.timeField.set(t.getEstimatedTime());
        this.infoArea.set(t.getRouteInformation());

    }
}
