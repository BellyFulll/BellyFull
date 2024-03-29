package com.example.bellyfull.modules.EmergencyAndHelp.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.PreLoginActivity;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.collection.User;
import com.example.bellyfull.data.firebase.ports.dbProfileCallback;
import com.example.bellyfull.data.firebase.repository.fbEmergencyImpl;
import com.example.bellyfull.data.firebase.repository.fbProfileRepositoryImpl;
import com.example.bellyfull.modules.Authentication.Fragments.EmailSender;
import com.example.bellyfull.utils.WeekCalculator;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class EmergencyActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_LOCATION_PERMISSION = 101;
    private static final String TAG = "EmergencyActivity";
    private static final String FB_TAG = "Firebase - Emergency";
    private String preferredHospitalPhoneNumber;
    private AlertDialog alertDialog;
    private Double latitude;
    private Double longitude;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_location);

        preferences = getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
        String userId = preferences.getString(preference_constant.pUserId, "");

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
            dbProfileCallback callback = new dbProfileCallback() {
                @Override
                public void onSuccess(User user) {
                    int pregnantWeeks;
                    long dateOfConception = user.getDateOfConception();
                    if (dateOfConception == 0) {
                        Toast.makeText(getApplicationContext(), "Suggestion: remember to set pregnant weeks to give better insights", Toast.LENGTH_LONG).show();
                        pregnantWeeks = 0;
                    } else {
                        WeekCalculator wc = new WeekCalculator(user.getDateOfConception());
                        pregnantWeeks = wc.calculateWeeksDifference();
                    }

                    String emergencyResponderEmail = user.getEmergencyResponderEmail();
                    if (emergencyResponderEmail == null) {
                        Toast.makeText(getApplicationContext(), "please fill in emergency responder email", Toast.LENGTH_LONG).show();
                        return;
                    }
                    EmailSender.sendEmergencyDataEmail(user.getEmergencyResponderEmail(), user.getName(), latitude, longitude, String.valueOf(pregnantWeeks));
                    Toast.makeText(getApplicationContext(), "Email sent to " + user.getEmergencyResponderEmail(), Toast.LENGTH_SHORT).show();
                }
            };
            fbProfileRepositoryImpl impl = new fbProfileRepositoryImpl(this);
            impl.getUserDetails(userId, callback);
        });
        Button vPreferredHospitalBtn = findViewById(R.id.vPrefHosp);
        vPreferredHospitalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbEmergencyImpl impl = new fbEmergencyImpl(getApplicationContext());
                impl.getUserHospital(userId, new dbProfileCallback() {
                    @Override
                    public void onSuccess(User user) {
                        if (user != null) {
                            preferredHospitalPhoneNumber = user.getPreferredHospitalContact();
                            Log.i(FB_TAG, "onClick: " + preferredHospitalPhoneNumber);
                            if (preferredHospitalPhoneNumber.equals("")) {
                                showLoginDialog();
                            } else {
                                initiatePhoneCall(preferredHospitalPhoneNumber);
                            }
                        }
                    }
                });
            }
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
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            LatLng currentLocation = new LatLng(latitude, longitude);
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
        NearbySearchAsyncTask nearbySearchAsyncTask = new NearbySearchAsyncTask(
                this, 5000, new NearbySearchAsyncTask.NearbySearchListener() {
            @Override
            public void onNearbySearchComplete(List<PlaceResult> placeResults) {
                updateMapWithPlaces(placeResults);
            }
        });
        nearbySearchAsyncTask.execute();
    }

    private void updateMapWithPlaces(List<PlaceResult> placeResults) {
        TextView mTextView = findViewById(R.id.mTextView);
        mTextView.setMovementMethod(new ScrollingMovementMethod());

        for (PlaceResult placeResult : placeResults) {
            SpannableString spannableString = new SpannableString(placeResult.getName() + "\n");
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown1)), 0, placeResult.getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, placeResult.getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            mTextView.append(spannableString);
            mTextView.append(placeResult.getAddress() + "\n");

            appendClickablePhoneNumber(mTextView, placeResult.getPhoneNumber());
            mTextView.append("\n");

            LatLng placeLatLng = new LatLng(placeResult.getLatitude(), placeResult.getLongitude());
            mMap.addMarker(new MarkerOptions().position(placeLatLng).title(placeResult.getName()));
        }
    }

    private void appendClickablePhoneNumber(TextView textView, final String phoneNumber) {
        SpannableString spannable = new SpannableString(phoneNumber);
        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                dialPhoneNumber(phoneNumber);
            }
        }, 0, phoneNumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.append(spannable);
        textView.append("\n");
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(dialIntent);
    }

    private void initiatePhoneCall(String phoneNumber) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(dialIntent);
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.req_login_dialog, null);
        builder.setView(dialogView);

        Button confirmButton = dialogView.findViewById(R.id.confirmButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preLoginIntent = new Intent(EmergencyActivity.this, PreLoginActivity.class);
                startActivity(preLoginIntent);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }
}


