package com.tourplanner.frontend.dal;

import com.tourplanner.shared.model.TourLogDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface TourLogApi {
    @POST("/tourLog/save")
    Call<TourLogDTO> createOrUpdateTourLog(@Body TourLogDTO tourLog);

    @GET("/tourLog/{id}")
    Call<TourLogDTO> getTourLog(@Path("id") UUID id);

    @GET("/tour/{tourId}/tourLogs")
    Call<List<TourLogDTO>> getTourLogs(@Path("tourId") UUID tourId);

    @DELETE( "/tourLog/{tourLogId}/delete")
    Call<UUID> deleteTourLog(@Path("tourLogId") UUID tourLogId);
}
