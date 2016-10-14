package com.example.kevinvelasco.icar;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ICarsService {


    @GET("/maps/api/directions/json")
    void getOverviewPolylone(@Query("origin") String origin, @Query("destination") String destination,
                             @Query("mode") String mode, Callback<DrivingResponse> callback);
}
