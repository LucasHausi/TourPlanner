package com.tourplanner.frontend.dal;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface MapApi {
    @GET
    Call<ResponseBody> fetchRoute(@Field("key") String key,@Field("from") String from,@Field("to") String to);
}
