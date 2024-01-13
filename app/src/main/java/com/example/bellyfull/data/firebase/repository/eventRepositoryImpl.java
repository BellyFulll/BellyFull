package com.example.bellyfull.data.firebase.repository;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodSession;

import com.example.bellyfull.Constant.db_collection_constant;
import com.example.bellyfull.data.firebase.collection.Event;
import com.example.bellyfull.data.firebase.firebase;
import com.example.bellyfull.data.firebase.ports.eventRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class eventRepositoryImpl implements eventRepository {
    Context context;
    FirebaseFirestore db = firebase.getDatabase();

    public eventRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void createEventInfo(Event event) {
        db.collection(db_collection_constant.EventCollection).document(event.getEventId())
                .set(event);
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

    @SuppressLint("RestrictedApi")
    public void getEventsForDate(Date date, EventCallback callback) {
        db.collection(db_collection_constant.EventCollection)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Event> events = new ArrayList<>();
                        Calendar selectedDateCalendar = Calendar.getInstance();
                        Calendar eventStartTimeCalendar = Calendar.getInstance();
                        selectedDateCalendar.setTime(date);
                        int selectedYear = selectedDateCalendar.get(Calendar.YEAR);
                        int selectedMonth = selectedDateCalendar.get(Calendar.MONTH);
                        int selectedDay = selectedDateCalendar.get(Calendar.DAY_OF_MONTH);
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Event event = document.toObject(Event.class);
                            eventStartTimeCalendar.setTime(new Date(event.getEventStartTime()));
                            int eventYear = eventStartTimeCalendar.get(Calendar.YEAR);
                            int eventMonth = eventStartTimeCalendar.get(Calendar.MONTH);
                            int eventDay = eventStartTimeCalendar.get(Calendar.DAY_OF_MONTH);
                            if(selectedYear == eventYear && selectedMonth == eventMonth && selectedDay == eventDay){
                                events.add(event);
                            }
                        }
                        // You may want to sort events by time or perform additional processing here
                        callback.onEventsRetrieved(events);
                    } else {
                        // Handle the error
                        Log.e("TESTING", "Error getting events for date", task.getException());
                    }
                });
    }

    public interface EventCallback {
        void onEventsRetrieved(List<Event> events);
    }

    private String formatDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    private String formatDateToStringLongForm(Date date) {
        System.out.println("[date]" + date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault());
        System.out.println(dateFormat.format(date).toString());
        return dateFormat.format(date);
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
