package com.tourplanner.frontend.bl;

import com.tourplanner.frontend.dal.MapApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.FileOutputStream;
import java.io.IOException;

public class MapServiceImp implements MapService{

    private final MapApi service;

    public MapServiceImp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.mapquestapi.com/directions/v2/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(MapApi.class);
    }
    @Override
    public void getMap(String from, String to) throws IOException {
        Call<ResponseBody> responseBodyCall = service.fetchRoute("yaHxV4XvjwMdBRxa1tkcXOs6dgaw3vg4", from, to);

        responseBodyCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                storeResponseInFile(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.err.println("API route call failed");
                t.printStackTrace();
            }
        });

    }
    private static void storeResponseInFile(Response<ResponseBody> response) {
        try (ResponseBody body = response.body()) {
            byte[] bytes = body.bytes();
            FileOutputStream fos = new FileOutputStream("src/main/resources/pictures/route.png");
            fos.write(bytes);
            fos.close();
            System.out.println("File Saved somewhere OO");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
