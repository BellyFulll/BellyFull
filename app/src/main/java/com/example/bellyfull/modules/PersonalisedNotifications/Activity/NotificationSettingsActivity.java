package com.example.bellyfull.modules.PersonalisedNotifications.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bellyfull.R;

public class NotificationSettingsActivity extends AppCompatActivity {

    private CheckBox enableRemindersCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        enableRemindersCheckBox = findViewById(R.id.enableRemindersCheckBox);

        // Load saved settings
        SharedPreferences sharedPreferences = getSharedPreferences("NotificationSettings", MODE_PRIVATE);
        boolean enableReminders = sharedPreferences.getBoolean("EnableReminders", true);

        // Update UI
        enableRemindersCheckBox.setChecked(enableReminders);

        Button btnSaveSettings = findViewById(R.id.btnSaveSettings);
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveSettingsClick(view);
            }
        });
    }

    public void onSaveSettingsClick(View view) {
        // Save settings
        SharedPreferences sharedPreferences = getSharedPreferences("NotificationSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("EnableReminders", enableRemindersCheckBox.isChecked());
        editor.apply();
    }
}
