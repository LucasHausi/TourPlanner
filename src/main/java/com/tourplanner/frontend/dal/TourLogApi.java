package com.tourplanner.frontend.dal;

import com.tourplanner.shared.model.TourLog;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface TourLogApi {
    @POST("/tourLog/save")
    Call<TourLog> createOrUpdateTourLog(@Body TourLog tourLog);

    @GET("/tourLog/{id}")
    Call<TourLog> getTourLog(@Path("id") UUID id);

    @GET("/tour/{tourId}/tourLogs")
    Call<List<TourLog>> getTourLogs(@Path("tourId") UUID tourId);

    @DELETE( "/tourLog/{tourLogId}/delete")
    Call<UUID> deleteTourLog(@Path("tourLogId") UUID tourLogId);

    @GET("/tourLogs")
    Call<List<TourLog>> getAllTourLogs();
}
