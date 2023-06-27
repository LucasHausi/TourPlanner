package com.tourplanner.frontend.dal;

import com.tourplanner.backend.dal.entity.TourLogEntity;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface TourLogApi {
    @POST("/tourLog/save")
    Call<TourLogEntity> createOrUpdateTourLog(@Body TourLogEntity tourLogEntity);

    @GET("/tourLog/{id}")
    Call<TourLogEntity> getTourLog(@Path("id") UUID id);

    @GET("/tour/{tourId}/tourLogs")
    Call<List<TourLogEntity>> getTourLogs(@Path("tourId") UUID tourId);

    @DELETE( "/tourLog/{tourLogId}/delete")
    Call<UUID> deleteTourLog(@Path("tourLogId") UUID tourLogId);

    @GET("/tourLogs")
    Call<List<TourLogEntity>> getAllTourLogs();
}
