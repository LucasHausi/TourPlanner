package com.tourplanner.viewmodel;

import com.tourplanner.model.Tour;
import com.tourplanner.repository.TourApi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.stream.Collectors;

public class MainWindowViewModel {
    TourApi service;

    public MainWindowViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30019")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(TourApi.class);
    }

    public ObservableList<String> getTourList() throws IOException {
       return service.getAllTours().execute().body().stream().map(Tour::getName).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
