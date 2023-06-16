package com.tourplanner.dal.repository;
import com.tourplanner.dal.entity.TourEntity;

import java.util.List;
import java.util.UUID;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TourApi {
    @POST("/newTour")
    Call<TourEntity> newTour(@Body TourEntity tourEntity);

    @GET("/tour/{id}")
    Call<TourEntity> getTour(@Path("id") UUID id);

    @GET("/allTours")
    Call<List<TourEntity>> getAllTours();
}
