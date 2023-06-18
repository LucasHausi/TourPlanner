package com.tourplanner.bl.service;

import com.tourplanner.dal.entity.TourLogEntity;
import com.tourplanner.dal.repository.TourLogApi;
import lombok.Getter;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

@Component
public class TourLogServiceImpl implements TourLogService {
    @Getter
    private final TourLogApi service;
    public TourLogServiceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30019")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(TourLogApi.class);
    }

    @Override
    public void add(TourLogEntity tourLogEntity) throws IOException {
        service.addTourLog(tourLogEntity).execute();
    }

    @Override
    public List<TourLogEntity> getAllTours() throws IOException {
        return service.getAllTourLogs().execute().body();
    }
}
