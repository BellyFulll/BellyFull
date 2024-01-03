package com.example.bellyfull.modules.EmergencyAndHelp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.net.PlacesClient;
import java.util.List;

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

        // Perform Nearby Search
        performNearbySearch();

        Button vMedInfoBtn = findViewById(R.id.vMedInfoBtn);
        vMedInfoBtn.setOnClickListener(view -> {
            Intent medicalInfoIntent = new Intent(this, MedicalInfoActivity.class);
            medicalInfoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // This flag starts the activity in a new task
            startActivity(medicalInfoIntent);
        });
        Button vPreferredHospitalBtn = findViewById(R.id.vPrefHosp);
        vPreferredHospitalBtn.setOnClickListener(view -> {
            Intent preferredHospitalIntent = new Intent(this, PreferredHospital.class);
            preferredHospitalIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // This flag starts the activity in a new task
            startActivity(preferredHospitalIntent);
        });
    }

    @Override
    public void onBackPressed() {
        // Finish the current activity and all activities below it in the stack
        finishAffinity();
    }

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
                this, 5000, new NearbySearchAsyncTask.NearbySearchListener() {
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
        TextView mTextView = findViewById(R.id.mTextView);
        mTextView.setMovementMethod(new ScrollingMovementMethod());

        for (PlaceResult placeResult : placeResults) {
            // Create a SpannableString to apply blue color and bold style to the hospital name
            SpannableString spannableString = new SpannableString(placeResult.getName() + "\n");
            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, placeResult.getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, placeResult.getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Append hospital name and address
            mTextView.append(spannableString);
            mTextView.append(placeResult.getAddress() + "\n");
            // Append hospital phone number (with a clickable link)
            appendClickablePhoneNumber(mTextView, placeResult.getPhoneNumber());
            mTextView.append("\n");

            // Add markers for each place
            LatLng placeLatLng = new LatLng(placeResult.getLatitude(), placeResult.getLongitude());
            mMap.addMarker(new MarkerOptions().position(placeLatLng).title(placeResult.getName()));
        }
    }

    private void appendClickablePhoneNumber(TextView textView, final String phoneNumber) {
        // Append phone number with a clickable link
        SpannableString spannable = new SpannableString(phoneNumber);
        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Handle the click event (e.g., open the dialer with the phone number)
                dialPhoneNumber(phoneNumber);
            }
        }, 0, phoneNumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Make the phone number appear as a clickable link
        textView.append(spannable);
        textView.append("\n");
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void dialPhoneNumber(String phoneNumber) {
        // Open the dialer with the provided phone number
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(dialIntent);
    }
}


