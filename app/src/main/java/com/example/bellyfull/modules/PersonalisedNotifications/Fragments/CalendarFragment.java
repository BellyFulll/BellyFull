package com.example.bellyfull.modules.PersonalisedNotifications.Fragments;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.collection.Event;
import com.example.bellyfull.data.firebase.repository.eventRepositoryImpl;
import com.example.bellyfull.modules.PersonalisedNotifications.Activity.NotificationSettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CalendarFragment extends Fragment {
    public View overlayView;
    public TextView TVDate;
    public TextView TVStartTime;
    public TextView TVEndTime;
    eventRepositoryImpl impl;
    private static final String CHANNEL_ID = "ReminderChannel";

    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        impl = new eventRepositoryImpl(getContext());
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TVDate = view.findViewById(R.id.TVDate);
        TVStartTime = view.findViewById(R.id.TVStartTime);
        TVEndTime = view.findViewById(R.id.TVEndTime);

        FloatingActionButton btnAddEvent = getView().findViewById(R.id.btnAddEvent);
        overlayView = getView().findViewById(R.id.overlayView);
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        Button settingsButton = view.findViewById(R.id.settingsButton);

//        EventInputFragment eventInputFragment = new EventInputFragment();
//        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//        eventInputFragmentIsCalled = fragmentManager.findFragmentById(R.id.FCVforInputEvent) == eventInputFragment;
//        while (eventInputFragmentIsCalled ==false){
//            overlayView.setVisibility(View.GONE);
//        }
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // This method will be called when the user selects a date in the CalendarView
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                retrieveAndDisplayEvents(selectedDate.getTime());
            }
        });
        overlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    btnAddEvent.setVisibility(View.VISIBLE);
                    hideEventInputFragment();
            }
        });
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddEvent.setVisibility(View.INVISIBLE);
                showEventInputFragment();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSettingsButtonClick();
            }
        });
        // Schedule health checkup reminder when the fragment is created
        scheduleHealthCheckupReminder();
    }
    private void showEventInputFragment() {
        overlayView.setVisibility(View.VISIBLE);
        EventInputFragment fragment = new EventInputFragment();

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.FCVforInputEvent, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void hideEventInputFragment() {
        overlayView.setVisibility(View.GONE);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.FCVforInputEvent);
        if (fragment != null) {
            transaction.remove(fragment);
            transaction.commit();
        }
    }
    private void retrieveAndDisplayEvents(Date selectedDate) {
        impl.getEventsForDate(selectedDate, new eventRepositoryImpl.EventCallback() {
            @Override
            public void onEventsRetrieved(List<Event> events) {
                // Update UI to display the events
                updateUIWithEvents(events);
            }
        });
    }
    private void updateUIWithEvents(List<Event> events) {
        LinearLayout eventContainer = getView().findViewById(R.id.eventContainer);
        eventContainer.removeAllViews();

        int marginBetweenEvents = getResources().getDimensionPixelSize(R.dimen.margin_between_events);

        for (Event event : events) {
            View eventView = LayoutInflater.from(requireContext()).inflate(R.layout.event_item, eventContainer, false);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 0, 0, marginBetweenEvents);
            eventView.setLayoutParams(layoutParams);

            TextView eventTitle = eventView.findViewById(R.id.eventTitle);
            TextView eventTime = eventView.findViewById(R.id.eventTime);
            TextView eventNote = eventView.findViewById(R.id.eventNote);

            String eventstarttime =event.getStartTime();
            String eventendtime = event.getEndTime();
            eventTitle.setText(event.getEventName());
            if((eventstarttime == null)&&(eventendtime == null)){
                eventTime.setVisibility(View.GONE);
            }if((eventstarttime != null)&&(eventendtime == null)){
                eventTime.setText(eventstarttime);
            }if ((eventstarttime == null)&&(eventendtime != null)){
                eventTime.setText("until "+ eventendtime);
            }if((eventstarttime != null)&&(eventendtime != null)) {
                eventTime.setText(eventstarttime + " - " + eventendtime);
            }

            String eventnote = event.getNote();
            if(eventnote==null){
                eventNote.setVisibility(View.GONE);
            }else {
                eventNote.setText(event.getNote());
            }

            eventContainer.addView(eventView);
        }
    }
    private void onSettingsButtonClick() {
        Intent intent = new Intent(requireContext(), NotificationSettingsActivity.class);
        startActivity(intent);
    }


    public class ReminderBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            showNotification(context, "Health Checkup Reminder", "Don't forget your health checkup!");
        }

        private void showNotification(Context context, String title, String message) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                // Create a notification channel (required for Android Oreo and above)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(
                            CHANNEL_ID,
                            "Reminder Channel",
                            NotificationManager.IMPORTANCE_DEFAULT
                    );
                    notificationManager.createNotificationChannel(channel);
                }

                // Create the notification
                Notification notification = new Notification.Builder(context, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setAutoCancel(true)  // Automatically removes the notification when tapped
                        .build();

                // Display the notification
                notificationManager.notify(0, notification);
            }
        }
    }

    //    private void addEventToCalendar() {
//        ContentResolver cr = requireActivity().getContentResolver();
//        ContentValues values = new ContentValues();
//        // Set your values for calendar event
//        values.put(CalendarContract.Events.TITLE, "Health Checkup");
//        // Add more details as needed
//
//        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
//    }
//
    private void scheduleHealthCheckupReminder() {
        long delayMillis = 10 * 1000; // 10 seconds

        Intent intent = new Intent(requireContext(), ReminderBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayMillis, pendingIntent);
        }
    }
}
