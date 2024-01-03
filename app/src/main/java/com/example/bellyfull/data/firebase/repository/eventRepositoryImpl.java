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

    public eventRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void addEvent(String userId, String note, String date, String time, String category) {
        FirebaseFirestore db = firebase.getDatabase();
        String eventID = UUID.randomUUID().toString();
        Event event = new Event(userId, note, date, time, category);

        db.collection(db_collection_constant.EventCollection).document(eventID.toString())
                .set(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Event Successfully Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to add event", Toast.LENGTH_SHORT).show();
                        Log.d("addEvent.failure", e.toString());
                    }
                });
    }
}
