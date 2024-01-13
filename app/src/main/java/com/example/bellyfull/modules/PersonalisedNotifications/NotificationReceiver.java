package com.example.bellyfull.modules.PersonalisedNotifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.bellyfull.Constant.notification_constant;
import com.example.bellyfull.R;

import java.math.BigInteger;
import java.util.UUID;

public class NotificationReceiver extends BroadcastReceiver {
    public static int notificationId = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        Notification notification = new NotificationCompat.Builder(context, notification_constant.EVENT_CHANNEL_ID)
                .setSmallIcon(R.drawable.momcta)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
        notificationId += 1;
    }
}
