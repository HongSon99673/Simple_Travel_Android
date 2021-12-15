package com.example.simpletravel.ui.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableFloat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletravel.R;
import com.example.simpletravel.databinding.ActivityGoogleMapBinding;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.my_interface.Constant;
import com.example.simpletravel.viewmodel.SearchViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.Permission;
import java.util.List;

import javax.inject.Provider;

public class GoogleMapActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private GoogleMap mGoogleMap;

    private GoogleApiClient mGoogleApiClient;

    private LatLng mCurrentLocation;

    private LatLng mLatLngSearchPosition;

    private ActivityGoogleMapBinding mActivityMainBinding;

    private LinearLayout radius15, radius3, radius5, radius10, radius30, radiusall;
    private TextView Back, Current;

    private ObservableFloat mRadiusSearch;

    private SearchViewModel searchViewModel;

    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private void initViews() {
        mActivityMainBinding= DataBindingUtil.setContentView(GoogleMapActivity.this, R.layout.activity_google_map);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment =
                (SupportMapFragment) fragmentManager.findFragmentById(R.id.map1);

        mapFragment.getMapAsync(this);//get google map default
        radius15 = (LinearLayout) mActivityMainBinding.radiusSearch15km;
        radius3 = (LinearLayout) mActivityMainBinding.radiusSearch3km;
        radius5 = (LinearLayout) mActivityMainBinding.radiusSearch5km;
        radius10 = (LinearLayout) mActivityMainBinding.radiusSearch10km;
        radius30 = (LinearLayout) mActivityMainBinding.radiusSearch30km;
        radiusall = (LinearLayout) mActivityMainBinding.radiusSearchAll;

        radius15.setOnClickListener(this);
        radius3.setOnClickListener(this);
        radius5.setOnClickListener(this);
        radius10.setOnClickListener(this);
        radius30.setOnClickListener(this);
        radiusall.setOnClickListener(this);
        //event back
        Back = findViewById(R.id.google_map_txt_Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//close activity google map
            }
        });
        //event click location now user
        Current = findViewById(R.id.google_map_txt_Location);
        Current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    final Location lastLocation =
                            LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (lastLocation == null) {
                        return;
                    }
                    mCurrentLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                    if (mLatLngSearchPosition == null) {
                        showCameraToPosition(mCurrentLocation, Constant.LEVEL_ZOOM_DEFAULT);
                    }
                }
            }
        });
        mRadiusSearch = new ObservableFloat(Constant.RADIUS_1_5KM);
        mActivityMainBinding.setRadiusSearch(mRadiusSearch.get());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            final Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            Toast.makeText(getApplicationContext(), "Coordinates" + lastLocation.getLatitude() + lastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            if (lastLocation == null) {
                return;
            }
            mCurrentLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            LocationVicinity(lastLocation.getLatitude(), lastLocation.getLongitude());
            if (mLatLngSearchPosition == null) {
                showCameraToPosition(mCurrentLocation, Constant.LEVEL_ZOOM_DEFAULT);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Lỗi kết nối: " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mLatLngSearchPosition = latLng;
        showMarkerToGoogleMap(mLatLngSearchPosition);

        if (mRadiusSearch.get() <= Constant.RADIUS_DEFAULT
                || mRadiusSearch.get() >= Constant.RADIUS_ALL) {
            showCameraToPosition(mLatLngSearchPosition, Constant.LEVEL_ZOOM_DEFAULT);
        } else {
            final LatLngBounds circleBounds = new LatLngBounds(
                    locationMinMax(false, mLatLngSearchPosition, mRadiusSearch.get()),
                    locationMinMax(true, mLatLngSearchPosition, mRadiusSearch.get()));
            showCameraToPosition(circleBounds, 200);
        }
        showCircleToGoogleMap(mLatLngSearchPosition, mRadiusSearch.get());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.setTrafficEnabled(true);
        mGoogleMap.setBuildingsEnabled(true);
        //check permission on or off
        if (ContextCompat.checkSelfPermission(GoogleMapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(GoogleMapActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);

        } else {
            //request permission
            Request_FINE_LOCATION(this,22);
            Request_COARSE_LOCATION(this,23);

        }
    }

    @Override
    public void onClick(View v) {
        float radius = Constant.RADIUS_DEFAULT;
        switch (v.getId()) {
            case R.id.radius_search_15km:
                radius = Constant.RADIUS_1_5KM;
                break;
            case R.id.radius_search_3km:
                radius = Constant.RADIUS_3KM;
                break;
            case R.id.radius_search_5km:
                radius = Constant.RADIUS_5KM;
                break;
            case R.id.radius_search_10km:
                radius = Constant.RADIUS_10KM;
                break;
            case R.id.radius_search_30km:
                radius = Constant.RADIUS_30KM;
                break;
            case R.id.radius_search_all:
                radius = Constant.RADIUS_ALL;
                break;
        }
        mRadiusSearch.set(radius);
        mActivityMainBinding.setRadiusSearch(mRadiusSearch.get());
        final LatLngBounds circleBounds;
        if (mLatLngSearchPosition == null) {
            mGoogleMap.clear();
            showCircleToGoogleMap(mCurrentLocation, mRadiusSearch.get());
            circleBounds =
                    new LatLngBounds(locationMinMax(false, mCurrentLocation, mRadiusSearch.get()),
                            locationMinMax(true, mCurrentLocation, mRadiusSearch.get()));
        } else {
            showMarkerToGoogleMap(mLatLngSearchPosition);
            showCircleToGoogleMap(mLatLngSearchPosition, mRadiusSearch.get());
            circleBounds = new LatLngBounds(
                    locationMinMax(false, mLatLngSearchPosition, mRadiusSearch.get()),
                    locationMinMax(true, mLatLngSearchPosition, mRadiusSearch.get()));
        }

        showCameraToPosition(circleBounds, 200);
        Toast.makeText(getApplicationContext(), "Coordinates 4" , Toast.LENGTH_SHORT).show();
    }

    public void showCameraToPosition(LatLng position, float zoomLevel) {
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(position)
                .zoom(zoomLevel)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        if (mGoogleMap != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), null);
        }
    }

    public void showCameraToPosition(LatLngBounds bounds, int padding) {
        if (mGoogleMap != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        }
    }
    //draw a circle when user selects search radius
    public void showCircleToGoogleMap(LatLng position, float radius) {
        if (position == null) {
            return;
        }
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(position);
        //Radius in meters
        circleOptions.radius(radius * 1000);
        circleOptions.fillColor(getResources().getColor(R.color.circle_on_map));
        circleOptions.strokeColor(getResources().getColor(R.color.circle_on_map));
        circleOptions.strokeWidth(0);
        if (mGoogleMap != null) {
            mGoogleMap.addCircle(circleOptions);
        }
    }
    //Show marker on Google Map When the user clicks on a location on the map, you want to display a marker on the map
    public void showMarkerToGoogleMap(LatLng position) {
        mGoogleMap.clear();
        MarkerOptions markerOptions = new MarkerOptions().position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_active));
        mGoogleMap.addMarker(markerOptions);
    }

    private LatLng locationMinMax(boolean positive, LatLng position, float radius) {
        double sign = positive ? 1 : -1;
        double dx = (sign * radius * 1000) / 6378000 * (180 / Math.PI);
        double lat = position.latitude + dx;
        double lon = position.longitude + dx / Math.cos(position.latitude * Math.PI / 180);
        return new LatLng(lat, lon);
    }
    //request permission user
    public static void Request_FINE_LOCATION(Activity act, int code)
    {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.ACCESS_FINE_LOCATION},code);
    }
    public static void Request_COARSE_LOCATION(Activity act, int code)
    {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.ACCESS_COARSE_LOCATION},code);
    }
    //function check location vicinity
    private void LocationVicinity(double lati, double longi){
        searchViewModel.getServices().observe(this, new Observer<List<Services>>() {
            @Override
            public void onChanged(List<Services> services) {
                for (int i =0; i< services.size(); i++){
                    Services services1 = services.get(i);
                    String latitude1 = String.valueOf(lati);
                    String longitude1 = String.valueOf(longi);
                    String latitude2 = String.valueOf(lati);
                    String longitude2 = String.valueOf(longi);

                    if (latitude1.trim().equals(latitude2.trim()) &&
                            longitude1.trim().equals(longitude2.trim())){
                            //show list service
                        //create marker
                        marker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(services1.getLatitude(), services1.getLongitude()))
                                .title(services1.getName())
                                .snippet(services1.getAddress())
                        );
                        Toast.makeText(getApplicationContext(), "Toa do" + services1.getLatitude(), Toast.LENGTH_SHORT).show();
                        Log.e("Toa do",services1.getLatitude().toString());
                        if (mLatLngSearchPosition == null) {
                            showCameraToPosition(mCurrentLocation, Constant.LEVEL_ZOOM_DEFAULT);
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Không có địa điểm nào gần đây !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}