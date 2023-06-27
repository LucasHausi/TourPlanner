package com.tourplanner.frontend.bl;

import com.tourplanner.backend.dal.entity.TourEntity;
import com.tourplanner.frontend.dal.TourApi;
import lombok.Getter;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class TourServiceImpl implements TourService {
    @Getter
    private final TourApi tourApi;
    public TourServiceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30019")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        tourApi = retrofit.create(TourApi.class);
    }

    @Override
    public void createOrUpdate(TourEntity tourEntity) throws IOException {
        tourApi.createOrUpdateTour(tourEntity).execute();
    }

    @Override
    public void deleteTour(UUID id) throws IOException {
        tourApi.deleteTour(id).execute();
    }

    @Override
    public List<TourEntity> getAllTours() throws IOException {
        return tourApi.getAllTours().execute().body();
    }
}
