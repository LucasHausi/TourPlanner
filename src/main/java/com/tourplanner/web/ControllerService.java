package com.tourplanner.web;
import com.tourplanner.model.Tour;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
@Component
public interface ControllerService {
    @POST("/newTour")
    Call<Tour> newTour(@Body Tour tour);

   @GET("/tour/{id}")
    Call<Tour> getTour(@Path("id") UUID id);

    @GET("/allTours")
    Call<List<Tour>> getAllTours();
}
