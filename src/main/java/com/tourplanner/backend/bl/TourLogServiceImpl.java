package com.tourplanner.backend.bl;

import com.tourplanner.backend.dal.entity.TourLogEntity;
import com.tourplanner.frontend.dal.TourLogApi;
import lombok.Getter;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

@Component
public class TourLogServiceImpl implements TourLogService {
    @Getter
    private final TourLogApi tourLogApi;
    public TourLogServiceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30019")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        tourLogApi = retrofit.create(TourLogApi.class);
    }

    @Override
    public void createOrUpdateTourLog(TourLogEntity tourLogEntity) throws IOException {
        tourLogApi.createOrUpdateTourLog(tourLogEntity).execute();
    }

    @Override
    public List<TourLogEntity> getAllTours() throws IOException {
        return tourLogApi.getAllTourLogs().execute().body();
    }
}
