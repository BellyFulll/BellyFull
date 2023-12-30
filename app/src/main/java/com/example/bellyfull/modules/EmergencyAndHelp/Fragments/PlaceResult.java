package com.example.bellyfull.modules.EmergencyAndHelp.Fragments;

public class PlaceResult {
    private String name;
    private String address;
    private String phoneNumber;
    private double latitude;
    private double longitude;

    public PlaceResult(String name, String address, String phoneNumber, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

