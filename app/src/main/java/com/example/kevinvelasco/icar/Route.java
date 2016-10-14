package com.example.kevinvelasco.icar;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kevinvelasco on 10/13/16.
 */

public class Route {

    @SerializedName("overview_polyline")
    OverviewPolyline polyline;

    public OverviewPolyline getPolyline() {
        return polyline;
    }

    public void setPolyline(OverviewPolyline polyline) {
        this.polyline = polyline;
    }
}
