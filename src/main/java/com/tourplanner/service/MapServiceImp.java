package com.tourplanner.service;

import com.tourplanner.dal.repository.MapApi;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MapServiceImp implements MapService{

    private final MapApi service;

    public MapServiceImp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.mapquestapi.com/directions/v2/route")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(MapApi.class);
    }
    @Override
    public String getMap(String from, String to) {
        service.fetchRoute("yaHxV4XvjwMdBRxa1tkcXOs6dgaw3vg4", from, to);
        return null;
    }
}
