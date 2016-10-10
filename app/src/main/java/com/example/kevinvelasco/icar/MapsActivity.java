package com.example.kevinvelasco.icar;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = MapsActivity.class.getSimpleName();
    public static final String CURRENT_LOCATION = "Current Location";

    GoogleMap mMap;
    GoogleApiClient googleApiClient;
    Location lastLocation;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        initToolbar();
        initNavDrawer();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = new SupportMapFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.navigation_fragment_container, mapFragment).commit();

        mapFragment.getMapAsync(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        buildGoogleApiClient();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Connected");
        requestLocationAndUpdateMaker();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection Suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();
    }

    public void initToolbar() {
        toolbar.setTitle(getString(R.string.app_name));
    }

    public void initNavDrawer() {
        navigationView.setNavigationItemSelectedListener(getNavigationItemListener());

        ActionBarDrawerToggle actionBarDrawerToggle = getActionBarDrawerToggle();

        //Setting the actionbarToggle to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }


    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we don't want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we don't want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
    }


    public NavigationView.OnNavigationItemSelectedListener getNavigationItemListener() {
        return item -> {

            switch (item.getItemId()) {
                case R.id.nav_item_current_location:
                    requestLocationAndUpdateMaker();
                    break;
                default:
                    //get string of city name
                    String cityName = item.getTitle().toString();
                    //update map marker
                    updateMapMarkerAndZoom(getLatLongFromCityName(cityName), cityName);
                    break;
            }

            // Highlight the selected item has been done by NavigationView
            item.setChecked(true);
            // Set action bar title
            toolbar.setTitle(item.getTitle());
            // Close the navigation drawer
            drawerLayout.closeDrawers();

            return true;
        };
    }

    public LatLng getLatLongFromCityName(String cityName) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = null;
        LatLng latLng = null;

        try {
            addressList = geocoder.getFromLocationName(cityName, 1);
        } catch (IOException e) {
            Snackbar.make(drawerLayout, "Unable to get lat and long of " + cityName, Snackbar.LENGTH_LONG)
                    .show();
            e.printStackTrace();
        }

        if (addressList != null && addressList.size() > 0) {
            latLng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
        }

        return latLng;
    }

    public void updateMapMarkerAndZoom(LatLng location, String makerTitle) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(location).title(makerTitle));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    public void requestLocationAndUpdateMaker() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    Log.i(TAG, "Permission result " + granted);
                    if (granted) {
                        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                        if (lastLocation != null) {
                            Log.d(TAG, "Location = " + lastLocation.toString());
                            LatLng location = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                            updateMapMarkerAndZoom(location, CURRENT_LOCATION);
                        } else {
                            Log.d(TAG, "No location detected");
                            Snackbar.make(drawerLayout, "No location detected", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    } else {
                        Snackbar.make(drawerLayout, "Permissions denied, can not get location", Snackbar.LENGTH_LONG)
                                .show();
                    }

                }, throwable -> {
                    Log.e(TAG, "onError", throwable);
                    Snackbar.make(drawerLayout, "Error attempting to get location", Snackbar.LENGTH_LONG)
                            .show();
                }, () -> Log.i(TAG, "OnComplete"));
    }
}
