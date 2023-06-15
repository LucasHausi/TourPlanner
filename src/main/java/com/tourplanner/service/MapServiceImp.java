package com.tourplanner.service;

import com.tourplanner.repository.TourApi;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MapServiceImp implements MapService{

    private final TourApi service;

    public MapServiceImp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30019")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(TourApi.class);
    }
    @Override
    public String getMap(String from, String to) {
        return null;
    }
}
