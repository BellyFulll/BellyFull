package com.example.bellyfull.data.firebase.ports;

import com.example.bellyfull.data.firebase.collection.User;

public interface dbProfileRepository {

    public void getUserDetails(String userId, dbProfileCallback callback);
}
