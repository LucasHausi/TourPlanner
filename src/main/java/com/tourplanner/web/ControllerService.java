package com.tourplanner.web;
import com.tourplanner.model.Tour;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ControllerService {
    @POST("/newTour")
    Call<Tour> newTour(@Body Tour tour);

   @GET("/tour/{id}")
   Tour getTour(@Path("id") UUID id);
}
