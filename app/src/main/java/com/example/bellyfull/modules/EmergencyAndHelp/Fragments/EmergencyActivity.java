package com.example.bellyfull.modules.EmergencyAndHelp.Fragments;

import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bellyfull.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.model.Place;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class EmergencyActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_LOCATION_PERMISSION = 101;
    private PlacesClient placesClient;
    private static final String TAG = "EmergencyActivity";
    private static Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_location);

        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        performNearbySearch();
    }


//        // Initialize the SDK
//        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
//        // Create a new Places client instance
//        placesClient = Places.createClient(this);
//        // Use fields to define the data types to return.
//        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.TYPES);
//        // Use the builder to create a FindCurrentPlaceRequest.
//        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);
//
//
//
//        // Call findCurrentPlace and handle the response (first check that the user has granted permission).
//if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//    Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
//    placeResponse.addOnCompleteListener(task -> {
//        if (task.isSuccessful()) {
//            FindCurrentPlaceResponse response = task.getResult();
//            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
//                String placeName = placeLikelihood.getPlace().getName();
//                List<String> placeTypes = placeLikelihood.getPlace().getPlaceTypes();
//                Log.i(TAG, "placeName: " + placeName);
//
//                if (placeTypes != null && placeTypes.contains(Place.Type.HOSPITAL.toString())) {
//                    // Update TextViews with the hospital information
//                    TextView mNameTextView = findViewById(R.id.mNameTextView);
//                    TextView mAddressTextView = findViewById(R.id.mAddressTextView);
//                    TextView mPhoneNumberTextView = findViewById(R.id.mPhoneTextView);
//
//                    mNameTextView.setText(placeLikelihood.getPlace().getName());
//                    mAddressTextView.setText(placeLikelihood.getPlace().getAddress());
//                    mPhoneNumberTextView.setText(placeLikelihood.getPlace().getPhoneNumber());
//
//                    // Add a marker for the hospital on the Google Map
//                    mMap.addMarker(new MarkerOptions()
//                            .title(placeName)
//                            .position(placeLikelihood.getPlace().getLatLng()));
//
//                    Log.i(TAG, "this code is running #4");
//
//                }
//            }
//        } else {
//            Exception exception = task.getException();
//            if (exception instanceof ApiException) {
//                ApiException apiException = (ApiException) exception;
//                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
//                Log.e(TAG, "Status message: " + apiException.getMessage());
//            }
//        }
//    });
//} else {
//    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
//}

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                .position(currentLocation)
                                .title("Current Location")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13));
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    private void performNearbySearch() {
        // Create an instance of NearbySearchAsyncTask
        NearbySearchAsyncTask nearbySearchAsyncTask = new NearbySearchAsyncTask(
                this, 10000, new NearbySearchAsyncTask.NearbySearchListener() {
            @Override
            public void onNearbySearchComplete(List<PlaceResult> placeResults) {
                // Handle the list of PlaceResult objects (e.g., update UI, display markers on the map)
                updateMapWithPlaces(placeResults);
            }
        });

        // Execute the task
        nearbySearchAsyncTask.execute();
    }

    private void updateMapWithPlaces(List<PlaceResult> placeResults) {

    TextView mNameTextView = findViewById(R.id.mNameTextView);
    TextView mAddressTextView = findViewById(R.id.mAddressTextView);
    TextView mPhoneNumberTextView = findViewById(R.id.mPhoneTextView);

    for (PlaceResult placeResult : placeResults) {
        // Append information to TextViews
        mNameTextView.append(placeResult.getName() + "\n");
        mAddressTextView.append(placeResult.getAddress() + "\n");
        mPhoneNumberTextView.append(placeResult.getPhoneNumber() + "\n");

        // Add markers for each place
        LatLng placeLatLng = new LatLng(placeResult.getLatitude(), placeResult.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(placeLatLng)
                .title(placeResult.getName()));

    }
    }
}


