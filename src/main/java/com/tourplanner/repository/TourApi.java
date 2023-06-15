package com.tourplanner.repository;
import com.tourplanner.model.Tour;

import java.util.List;
import java.util.UUID;


import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TourApi {
    @POST("/newTour")
    Call<Tour> newTour(@Body Tour tour);

   @GET("/tour/{id}")
    Call<Tour> getTour(@Path("id") UUID id);

    @GET("/allTours")
    Call<List<Tour>> getAllTours();
}