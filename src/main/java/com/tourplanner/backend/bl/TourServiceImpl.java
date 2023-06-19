package com.tourplanner.backend.bl;

import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.frontend.dal.TourApi;
import lombok.Getter;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

@Component
public class TourServiceImpl implements TourService {
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
    public void add(TourEntity tourEntity) throws IOException {
        service.newTour(tourEntity).execute();
    }

    @Override
    public List<TourEntity> getAllTours() throws IOException {
        return service.getAllTours().execute().body();
    }
}
