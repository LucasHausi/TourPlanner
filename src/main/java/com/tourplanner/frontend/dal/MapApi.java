package com.tourplanner.frontend.dal;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapApi {
    @GET("/staticmap/v5/map")
    Call<ResponseBody> fetchRoute(@Query("key")  String key, @Query("start") String from, @Query("end") String to,
                                  @Query("size") String size);
    @GET("/directions/v2/route")
    Call<ResponseBody> getDistanceAndTime(@Query("key") String key, @Query("from") String from, @Query("to") String to,
                                          @Query("unit") String unit);
}
