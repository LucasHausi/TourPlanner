package com.tourplanner.repository;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapApi {
    @GET("468x480.png")
    Call<ResponseBody> fetchImage(@Query("text") String text);
}
