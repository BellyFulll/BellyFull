package com.example.bellyfull.data.firebase.ports;

import com.example.bellyfull.data.firebase.collection.Event;

public interface eventRepository {
    void createEventInfo(Event event);

    void setEventInfoEventName(String eventId, String eventName);

    void setEventInfoNote(String eventId, String note);

    void setEventInfoDate(String eventId, String date);

    void setEventInfoStartTime(String eventId, String startTime);

    void setEventInfoEndTime(String eventId, String endTime);

    void setEventInfoCategory(String eventId, String category);

//    public void addEvent(String userId, String note, String date, String startTime, String endTime, String category);
}
