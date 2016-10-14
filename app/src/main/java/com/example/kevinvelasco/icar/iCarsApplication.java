package com.example.kevinvelasco.icar;

import android.app.Application;

import retrofit.RestAdapter;


public class iCarsApplication extends Application {

    private static ICarsService iCarsService;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static ICarsService getICarsService(){
        if (iCarsService == null) {
            RestAdapter restAdapter= new RestAdapter.Builder()
                    .setEndpoint("https://maps.googleapis.com")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            iCarsService = restAdapter.create(ICarsService.class);
        }

        return iCarsService;
    }
}
