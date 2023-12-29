package com.example.bellyfull.data.firebase.ports;

public interface fbSignUpRepository {
    public void registerUser(String name, String email, String password);
}
