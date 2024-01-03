package com.example.bellyfull.data.firebase.ports;

public interface eventRepository {
    public void addEvent(String userId,String note, String date, String time, String category);
}
