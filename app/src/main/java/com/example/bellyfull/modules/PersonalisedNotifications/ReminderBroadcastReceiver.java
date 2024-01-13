package com.example.bellyfull.modules.PersonalisedNotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.bellyfull.R;

public class ReminderBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Triggered when the alarm fires, show the notification here
//        showNotification(context, "Reminder", "It's time for your event!");
    }

//    private void showNotification(Context context, String title, String message) {
//        // Build and show the notification
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
//                .setSmallIcon(R.drawable.ic_notification)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(1, builder.build());
//    }
}
