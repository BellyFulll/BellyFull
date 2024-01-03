package com.example.bellyfull.data.firebase.collection;

public class Event {
    private String eventName;
    private String note;
    private String date;
    private String time;
    private String userId;
    private String category;
    public Event (String eventName, String date, String time, String userId, String category){
        this.date = date;
    }

    public Event (){
    }

    public void setEventName(String eventName,String userId) {
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

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getUserId() {
        return userId;
    }
}
