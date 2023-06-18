package com.tourplanner.dal.repository;

import com.tourplanner.dal.entity.TourLogEntity;
import org.springframework.web.bind.annotation.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import java.util.List;
import java.util.UUID;

public interface TourLogApi {
    @POST("/tourLog/add")
    Call<TourLogEntity> addTourLog(@RequestBody TourLogEntity tourLogEntity);

    @GET("/tourLog/{id}")
    Call<TourLogEntity> getTourLog(@Path("id") UUID id);

    @GET("/tour/{tourId}/tourLogs")
    Call<List<TourLogEntity>> getTourLogs(@Path("tourId") UUID tourId);

    @GET("/tourLogs")
    Call<List<TourLogEntity>> getAllTourLogs();
}