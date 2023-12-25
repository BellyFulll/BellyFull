package com.example.bellyfull.data.firebase.collection;

import java.util.UUID;

public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String contact;
    private String address;

    public User(String userId, String name, String email, String password, String contact, String address) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.address = address;
    }

    public User() {
        // public no-args constructor needed
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
