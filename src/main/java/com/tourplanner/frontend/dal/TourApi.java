package com.tourplanner.frontend.dal;

import java.util.List;
import java.util.UUID;


import com.tourplanner.shared.model.TourDTO;
import retrofit2.Call;
import retrofit2.http.*;

public interface TourApi {
    @POST("/tour/save")
    Call<TourDTO> createOrUpdateTour(@Body TourDTO tour);

    @DELETE( "/tour/{tourId}/delete")
    Call<UUID> deleteTour(@Path("tourId") UUID tourId);

    @GET("/tour/{id}")
    Call<TourDTO> getTour(@Path("id") UUID id);

    @GET("/allTours")
    Call<List<TourDTO>> getAllTours();
}
