package com.tourplanner.frontend.bl.service;

import com.tourplanner.frontend.dal.TourLogApi;
import com.tourplanner.frontend.bl.mapper.TourLogMapper;
import com.tourplanner.frontend.bl.model.Tour;
import com.tourplanner.frontend.bl.model.TourLog;
import lombok.Getter;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TourLogServiceImpl implements TourLogService {
    @Getter
    private final TourLogApi tourLogApi;

    private final TourLogMapper tourLogMapper = Mappers.getMapper(TourLogMapper.class);

    public TourLogServiceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30019")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        tourLogApi = retrofit.create(TourLogApi.class);
    }

    @Override
    public void createOrUpdateTourLog(TourLog tourLog) throws IOException {
       tourLogApi.createOrUpdateTourLog(tourLogMapper.toDTO(tourLog)).execute();
    }

    @Override
    public void deleteTourLog(UUID tourLogId) throws IOException {
        tourLogApi.deleteTourLog(tourLogId).execute();
    }

    @Override
    public List<TourLog> getAllTours() throws IOException {
        return tourLogApi.getAllTourLogs().execute().body().stream()
                .map(tourLogMapper::fromDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TourLog> getAllTourLogsOfTour(Tour tour) throws IOException {
        return tourLogApi.getTourLogs(tour.getId()).execute().body().stream()
                .map(tourLogMapper::fromDTO)
                .collect(Collectors.toList());
    }
}
