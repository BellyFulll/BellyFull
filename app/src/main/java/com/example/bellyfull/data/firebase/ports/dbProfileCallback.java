package com.example.bellyfull.data.firebase.ports;

import com.example.bellyfull.data.firebase.collection.User;

public interface dbProfileCallback {
    void onSuccess(User user);
}
