package com.example.bellyfull.modules.EmergencyAndHelp.Fragments;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.bellyfull.R;
import com.example.bellyfull.modules.EmergencyAndHelp.Fragments.EmergencyActivity;

/**
 * Implementation of App Widget functionality.
 */
public class EmergencyWidget extends AppWidgetProvider {
    static final String TAG = "EmergencyWidget";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {


        CharSequence widgetText = context.getString(R.string.appwidget_text);

        // Construct the Intent object linking to EmergencyActivity
        Intent intent = new Intent(context, EmergencyActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.emergency_widget);
        views.setOnClickPendingIntent(R.id.widget_button, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d(TAG, "updateAppWidget: Updating widget");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}