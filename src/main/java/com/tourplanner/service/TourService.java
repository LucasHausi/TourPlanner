package com.tourplanner.service;

import com.tourplanner.model.Tour;
import com.tourplanner.web.ControllerService;
import lombok.Getter;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Component
public class TourService implements IService<Tour>{
    @Getter
    private final ControllerService service;
    public TourService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30019")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(ControllerService.class);
    }

    @Override
    public void add(Tour tour) throws IOException {
        service.newTour(tour).execute();
    }
}
