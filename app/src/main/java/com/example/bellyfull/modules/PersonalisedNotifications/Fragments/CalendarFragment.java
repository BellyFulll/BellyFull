package com.example.bellyfull.modules.PersonalisedNotifications.Fragments;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.collection.Event;
import com.example.bellyfull.data.firebase.repository.eventRepositoryImpl;
import com.example.bellyfull.modules.PersonalisedNotifications.InputBottomSheet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CalendarFragment extends Fragment {
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
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        retrieveAndDisplayEvents(new Date());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                retrieveAndDisplayEvents(selectedDate.getTime());
            }
        });

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });

        // Schedule health checkup reminder when the fragment is created
        scheduleHealthCheckupReminder();
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        InputBottomSheet inputBottomSheet = new InputBottomSheet(dialog);
        inputBottomSheet.setUp();

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void retrieveAndDisplayEvents(Date selectedDate) {
        impl.getEventsForDate(selectedDate, new eventRepositoryImpl.EventCallback() {
            @Override
            public void onEventsRetrieved(List<Event> events) {
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

            TextView TVEventTitle = eventView.findViewById(R.id.eventTitle);
            TextView TVEventTime = eventView.findViewById(R.id.eventTime);
            TextView TVEventNote = eventView.findViewById(R.id.eventNote);

            ImageView IVEventCategoryIcon = eventView.findViewById(R.id.IVEventCategoryIcon);
            String iconColor = event.getIconColor();
            System.out.println(iconColor);
            if (iconColor != null) {
                IVEventCategoryIcon.setColorFilter(Color.parseColor(event.getIconColor()));
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(event.getEventStartTime()));

            String eventStartTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());

            calendar.setTime(new Date(event.getEventEndTime()));
            String eventEndTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());

            TVEventTitle.setText(event.getEventName());
            TVEventTime.setText(String.format("%s - %s", eventStartTime, eventEndTime));

            String eventNote = event.getEventNote();
            if (eventNote == null) {
                TVEventNote.setVisibility(View.GONE);
            } else {
                TVEventNote.setText(event.getEventNote());
            }

            eventContainer.addView(eventView);
        }
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
                        .setAutoCancel(true)
                        .build();

                // Display the notification
                notificationManager.notify(0, notification);
            }
        }
    }

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
