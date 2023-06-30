package com.tourplanner.frontend.bl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourplanner.frontend.dal.MapApi;

import javafx.util.Pair;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class MapServiceImp implements MapService{

    private final MapApi service;

    public MapServiceImp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.mapquestapi.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(MapApi.class);
    }
    @Override
    public void getMap(UUID id, String from, String to) throws IOException {
        Call<ResponseBody> responseBodyCall = service.fetchRoute("yaHxV4XvjwMdBRxa1tkcXOs6dgaw3vg4", from, to, "390,360");

        responseBodyCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                storeResponseInFile(response,id, from,to);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.err.println("API route call failed");
                t.printStackTrace();
            }
        });

    }

    @Override
    public String[] getDistanceAndTime(String from, String to) throws IOException {
        Response<ResponseBody> response = service.getDistanceAndTime("yaHxV4XvjwMdBRxa1tkcXOs6dgaw3vg4",from,to).execute();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode resultObj = mapper.readTree(response.body().string());
        String[] destAndTime = new String[2];
        destAndTime[0] = resultObj.get("route").get("distance").toString();
        destAndTime[1] = resultObj.get("route").get("realTime").toString();
        return destAndTime;
    }

    private static void storeResponseInFile(Response<ResponseBody> response,UUID id, String from, String to) {
        try (ResponseBody body = response.body()) {
            byte[] bytes = body.bytes();
            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir").toString()+"/images/Route"+id+".png");
            fos.write(bytes);
            fos.close();
            Publisher.notifySubscribers(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
