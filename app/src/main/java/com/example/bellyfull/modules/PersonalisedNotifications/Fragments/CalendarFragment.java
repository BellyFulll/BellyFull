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
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bellyfull.R;
import com.example.bellyfull.modules.PersonalisedNotifications.Activity.NotificationSettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CalendarFragment extends Fragment {
    public View overlayView;
    public TextView TVDate;
    public TextView TVStartTime;
    public TextView TVEndTime;
//    boolean eventInputFragmentIsCalled;
    private static final String CHANNEL_ID = "ReminderChannel";

    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        impl = new eventRepositoryImpl(getContext());
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

    public View getOverlayView() {
        return overlayView;
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
