package com.tourplanner.frontend.dal;
import com.tourplanner.backend.dal.entity.TourEntity;

import java.util.List;
import java.util.UUID;


import retrofit2.Call;
import retrofit2.http.*;

public interface TourApi {
    @POST("/tour/save")
    Call<TourEntity> createOrUpdateTour(@Body TourEntity tourEntity);

    @DELETE( "/tour/{tourId}/delete")
    Call<UUID> deleteTour(@Path("tourId") UUID tourId);

    @GET("/tour/{id}")
    Call<TourEntity> getTour(@Path("id") UUID id);

    @GET("/allTours")
    Call<List<TourEntity>> getAllTours();
}
