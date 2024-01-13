package com.example.bellyfull.data.firebase.collection;

import android.graphics.Color;

public class Event {
    private String eventId;
    private String eventName;
    private String eventNote;
    private String eventDate;
    private String eventStartTime;
    private String eventEndTime;
    private String userId;
    private String eventCategory;
    private Color categoryColor;

    public Event(String eventId, String eventName, String eventDate, String eventStartTime, String eventEndTime, String eventCategory, String userId) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventCategory = eventCategory;
        this.userId = userId;
    }

    public Event() {
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
        this.userId = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventNote(String eventNote) {
        this.eventNote = eventNote;
    }

    public String getEventNote() {
        return eventNote;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setCategoryColor(Color color) {
        this.categoryColor = color;
    }

    public Color getCategoryColor(Color color) {
        return this.categoryColor;
    }

    public void setUserId() {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
