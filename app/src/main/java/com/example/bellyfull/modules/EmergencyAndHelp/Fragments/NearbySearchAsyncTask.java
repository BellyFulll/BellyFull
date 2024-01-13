package com.example.bellyfull.modules.EmergencyAndHelp.Fragments;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bellyfull.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NearbySearchAsyncTask extends AsyncTask<Void, Void, List<PlaceResult>> {
    private static final String TAG = "NearbySearchAsyncTask";
    private Context context;
    private int radius;
    private NearbySearchListener listener;

    public NearbySearchAsyncTask(Context context, int radius, NearbySearchListener listener) {
        this.context = context;
        this.radius = radius;
        this.listener = listener;
    }

    @Override
    protected List<PlaceResult> doInBackground(Void... voids) {
        List<PlaceResult> placeResults = new ArrayList<>();
        try {
            // Obtain the current location
            Location currentLocation = getCurrentLocation();
            Log.i(TAG, "Current location: " + currentLocation);

            if (currentLocation != null) {
                // Use the current location for the Nearby Search
                double currentLatitude = currentLocation.getLatitude();
                double currentLongitude = currentLocation.getLongitude();

                // Construct the Nearby Search URL
                String apiUrl = String.format("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&radius=%d&type=hospital&key=%s",
                        currentLatitude, currentLongitude, radius, context.getString(R.string.google_maps_key));

                // Open connection
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                Log.i(TAG, "Nearby Search URL: " + url.toString());

                // Read the response
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder responseStringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    responseStringBuilder.append(line);
                }

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(responseStringBuilder.toString());
                Log.i(TAG, "Nearby Search JSON response: " + jsonResponse.toString());

                // Extract relevant information
                JSONArray resultsArray = jsonResponse.getJSONArray("results");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject placeObject = resultsArray.getJSONObject(i);

                    // Check if the place is a hospital
                    JSONArray typesArray = placeObject.getJSONArray("types");
                    boolean isHospital = isHospital(typesArray);

                    if (isHospital) {
                        String placeId = placeObject.getString("place_id");
                        String name = placeObject.getString("name");
                        String address = placeObject.optString("vicinity", "N/A");
                        double latitude = placeObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                        double longitude = placeObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                        // skip if name contains Dr.
                        if (name.contains("Dr.")) {
                            continue;
                        }

                        // Fetch additional details using Place Details API
                        PlaceResult placeResult = fetchPlaceDetails(placeId, name, address, latitude, longitude);
                        if (placeResult != null) {
                            placeResults.add(placeResult);
                        }
                    }
                }
            }
        } catch (IOException | JSONException e) {Log.e(TAG, "Error performing Nearby Search: " + e.getMessage());}
        return placeResults;
    }

    private boolean isHospital(JSONArray typesArray) {
        // Check if the types array contains "hospital"
        for (int j = 0; j < typesArray.length(); j++) {
            try {
                String type = typesArray.getString(j);
                if (type.equals("hospital")) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private Location getCurrentLocation() {
        // Use the LocationManager to obtain the current location
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            // Check if the location providers are enabled
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled || isNetworkEnabled) {
                // Use the best available provider
                String provider = (isGPSEnabled) ? LocationManager.GPS_PROVIDER : LocationManager.NETWORK_PROVIDER;
                return locationManager.getLastKnownLocation(provider);
            }
        } catch (SecurityException e) {
            Log.e(TAG, "Error obtaining current location: " + e.getMessage());
        }

        return null;
    }

    private PlaceResult fetchPlaceDetails(String placeId, String name, String address, double latitude, double longitude) {
        try {
            String apiUrl = String.format("https://maps.googleapis.com/maps/api/place/details/json?place_id=%s&fields=name,formatted_phone_number&key=%s",
                    placeId, context.getString(R.string.google_maps_key));
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder responseStringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseStringBuilder.append(line);
            }

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(responseStringBuilder.toString());
            Log.i(TAG, "Place Details JSON response: " + jsonResponse.toString());

            // Extract phone number from details
            String phoneNumber = jsonResponse.optJSONObject("result").optString("formatted_phone_number", "N/A");

            return new PlaceResult(name, address, phoneNumber, latitude, longitude);
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error fetching Place Details: " + e.getMessage());
            return null;
        }
    }

    public interface NearbySearchListener {
        void onNearbySearchComplete(List<PlaceResult> placeResults);
    }

    @Override
    protected void onPostExecute(List<PlaceResult> placeResults) {
        if (listener != null) {
            listener.onNearbySearchComplete(placeResults);
        }
    }
}

