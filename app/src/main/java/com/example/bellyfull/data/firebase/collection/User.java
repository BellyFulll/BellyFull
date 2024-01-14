package com.example.bellyfull.data.firebase.collection;

public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String contact;
    private String address;

    private String preferredHospitalContact;
    private String emergencyResponderEmail;
    private long dateOfConception;

    public User(String userId, String name, String email, String password, String contact, String address, String preferredHospitalContact, String emergencyResponderEmail, long dateOfConception) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.address = address;
        this.preferredHospitalContact = preferredHospitalContact;
        this.emergencyResponderEmail = emergencyResponderEmail;
        this.dateOfConception = dateOfConception;
    }

    public User() {
        // public no-args constructor needed
    }

    public String getEmergencyResponderEmail() {
        return emergencyResponderEmail;
    }

    public void setEmergencyResponderEmail(String emergencyResponderEmail) {
        this.emergencyResponderEmail = emergencyResponderEmail;
    }

    public long getDateOfConception() {
        return dateOfConception;
    }

    public void setDateOfConception(long dateOfConception) {
        this.dateOfConception = dateOfConception;
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

    public String getPreferredHospitalContact() {
        return preferredHospitalContact;
    }

    public void setPreferredHospitalContact(String preferredHospitalContact) {
        this.preferredHospitalContact = preferredHospitalContact;
    }
}
