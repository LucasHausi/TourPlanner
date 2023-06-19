package com.tourplanner.backend.bl;

import com.tourplanner.frontend.dal.MapApi;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

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
    public String getMap(String from, String to) throws IOException {
        return service.fetchRoute("yaHxV4XvjwMdBRxa1tkcXOs6dgaw3vg4", from, to).execute().body().string();
    }
}
