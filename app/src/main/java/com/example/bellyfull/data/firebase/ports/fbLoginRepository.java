package com.example.bellyfull.data.firebase.ports;

public interface fbLoginRepository {
    public void getPassword(String email, dbLoginCallback callback);
}
