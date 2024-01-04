package com.example.bellyfull.data.firebase.ports;

import com.example.bellyfull.data.firebase.collection.User;

public interface dbProfileRepository {
    public void getUserDetails(String userId, dbProfileCallback callback);

    public void updateUserName(String userId, String name);
    public void updateUserEmail(String userId, String email);
    public void updateUserContact(String userId, String contact);
    public void updateUserAddress(String userId, String address);
    public void updateUserPreferredHospitalContact(String userId, String preferredHospitalContact);

    void updatePassword(String userId, String password);
}
