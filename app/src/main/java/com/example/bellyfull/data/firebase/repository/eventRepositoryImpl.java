package com.example.bellyfull.data.firebase.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bellyfull.Constant.db_collection_constant;
import com.example.bellyfull.data.firebase.collection.Event;
import com.example.bellyfull.data.firebase.firebase;
import com.example.bellyfull.data.firebase.ports.eventRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class eventRepositoryImpl implements eventRepository {
    Context context;
    FirebaseFirestore db = firebase.getDatabase();

    public eventRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void createEventInfo(String userId, String eventId) {
        Event eventInfo = new Event(eventId, null, null, null, null, null, userId);
        db.collection(db_collection_constant.EventCollection).document(eventId.toString())
                .set(eventInfo);
    }

    @Override
    public void setEventInfoEventName(String eventId, String eventName) {
        db.collection(db_collection_constant.EventCollection).document(eventId)
                .update("eventName", eventName);
    }
@Override
    public void setEventInfoNote(String eventId, String note) {
        db.collection(db_collection_constant.EventCollection).document(eventId)
                .update("note", note);
    }
@Override
    public void setEventInfoDate(String eventId, String date) {
        db.collection(db_collection_constant.EventCollection).document(eventId)
                .update("date", date);
    }
@Override
    public void setEventInfoStartTime(String eventId, String startTime) {
        db.collection(db_collection_constant.EventCollection).document(eventId).update("startTime", startTime);
    }
@Override
    public void setEventInfoEndTime(String eventId, String endTime) {
        db.collection(db_collection_constant.EventCollection).document(eventId).update("endTime", endTime);
    }
    @Override
    public void setEventInfoCategory(String eventId, String category) {
        db.collection(db_collection_constant.EventCollection).document(eventId).update("category", category);
    }

//    @Override
//    public void addEvent(String userId, String note, String date, String startTime, String endTime, String category) {
//        FirebaseFirestore db = firebase.getDatabase();
//        String eventID = UUID.randomUUID().toString();
//        Event event = new Event(eventID, note, date, startTime, endTime, category, userId);
//
//        db.collection(db_collection_constant.EventCollection).document(eventID.toString())
//                .set(event)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(context, "Event Successfully Added", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, "Failed to add event", Toast.LENGTH_SHORT).show();
//                        Log.d("addEvent.failure", e.toString());
//                    }
//                });
//    }
}
