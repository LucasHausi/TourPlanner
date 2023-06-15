package com.tourplanner.service;

import com.tourplanner.model.Tour;
import com.tourplanner.repository.TourApi;
import lombok.Getter;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Component
public class TourServiceImpl implements TourService<Tour> {
    @Getter
    private final TourApi service;
    public TourServiceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30019")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(TourApi.class);
    }

    @Override
    public void add(Tour tour) throws IOException {
        service.newTour(tour).execute();
    }
}
