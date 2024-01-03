package com.example.bellyfull.data.firebase.ports;

import com.example.bellyfull.data.firebase.collection.User;

public interface dbEmergency {
    public void getUserHospital(String userId, dbProfileCallback callback);
}
