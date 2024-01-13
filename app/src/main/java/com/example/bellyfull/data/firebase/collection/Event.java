package com.example.bellyfull.data.firebase.collection;

import android.graphics.Color;

public class Event {
    private String eventId;
    private String eventName;
    private String eventNote;
    private long eventStartTime;
    private long eventEndTime;
    private String userId;
    private String eventCategory;
    private Color iconColor;

    public Event(String eventId, String eventName, long eventStartTime, long eventEndTime, String eventCategory, String userId) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventCategory = eventCategory;
        this.userId = userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Color getIconColor() {
        return iconColor;
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

    public void setEventStartTime(long eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public long getEventStartTime() {
        return eventStartTime;
    }

    public void setEventEndTime(long eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public long getEventEndTime() {
        return eventEndTime;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setIconColor(Color color) {
        this.iconColor = color;
    }

    public Color getCategoryColor(Color color) {
        return this.iconColor;
    }

    public void setUserId() {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
