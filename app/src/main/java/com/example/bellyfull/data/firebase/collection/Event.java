package com.example.bellyfull.data.firebase.collection;

import android.graphics.Color;

public class Event {
    private String eventId;
    private String eventName;
    private String note;
    private String date;
    private String startTime;
    private String endTime;
    private String userId;
    private String category;
    private Color categoryColor;

    public Event(String eventId, String eventName, String date, String startTime, String endTime, String category, String userId) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
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

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
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
