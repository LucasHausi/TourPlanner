package com.tourplanner.frontend.bl;

import com.tourplanner.frontend.dal.TourLogApi;
import com.tourplanner.shared.model.Tour;
import com.tourplanner.shared.model.TourLog;
import lombok.Getter;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
    public void createOrUpdateTourLog(TourLog tourLog) throws IOException {
        tourLogApi.createOrUpdateTourLog(tourLog).execute();
    }

    @Override
    public void deleteTourLog(UUID tourLogId) throws IOException {
        tourLogApi.deleteTourLog(tourLogId).execute();
    }

    @Override
    public List<TourLog> getAllTours() throws IOException {
        return tourLogApi.getAllTourLogs().execute().body();
    }

    @Override
    public List<TourLog> getAllTourLogsOfTour(Tour tour) throws IOException {
        return tourLogApi.getTourLogs(tour.getId()).execute().body();
    }
}
