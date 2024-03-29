package com.example.bellyfull.modules.PersonalisedNotifications;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.bellyfull.Constant.notification_constant;
import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.collection.Event;
import com.example.bellyfull.data.firebase.repository.eventRepositoryImpl;
import com.example.bellyfull.utils.AlertDialogUtil;
import com.example.bellyfull.utils.convertHexToIntUtil;
import com.example.bellyfull.utils.showColorPickerUtil;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class InputBottomSheet implements DatePickerDialog.OnDateSetListener {
    Dialog dialog;
    eventRepositoryImpl impl;
    private TextView TVDate;
    private TextView TVStartTime;
    private TextView TVEndTime;
    private Context context;
    private Set<String> eventCategories;
    private Set<EventCategory> defaultEventCategories;
    private View lastSelectedEventCategoryCard = null;
    private EventCategory lastSelectedEventCategory = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LinearLayout cardContainer;
    private Date selectedDate;
    private Date selectedStartTime;
    private Date selectedEndTime;

    public InputBottomSheet(Dialog dialog) {
        defaultEventCategories = new HashSet<>();
        defaultEventCategories.add(new EventCategory("#FDD0EC", "#DA62AC", "#943D73", "checkups"));

        this.dialog = dialog;
        this.context = dialog.getContext();
        sharedPreferences = dialog.getContext().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
        eventCategories = sharedPreferences.getStringSet(
                preference_constant.pEventCategories,
                convertEventCategoriesToStringSet(defaultEventCategories)
        );
        editor = sharedPreferences.edit();
        editor.putStringSet(preference_constant.pEventCategories, eventCategories);
        editor.commit();
        impl = new eventRepositoryImpl(dialog.getContext());
    }

    private static Set<String> convertEventCategoriesToStringSet(Set<EventCategory> eventCategories) {
        Set<String> stringSet = new HashSet<>();
        String stringed;
        for (EventCategory category : eventCategories) {
            stringed = category.getPrimaryColor() + "," + category.getSelectedColor() + "," + category.getIconColor() + "," + category.getEventCategoryName();
            stringSet.add(stringed);
        }
        return stringSet;
    }

    private static Set<EventCategory> convertStringSetToEventCategories(Set<String> stringSet) {
        Set<EventCategory> eventCategories = new HashSet<>();
        for (String str : stringSet) {
            String[] parts = str.split(",");
            if (parts.length == 4) {
                String color1 = parts[0];
                String color2 = parts[1];
                String color3 = parts[2];
                String name = parts[3];
                EventCategory category = new EventCategory(color1, color2, color3, name);
                eventCategories.add(category);
            }
        }
        return eventCategories;
    }

    private void addCategory(EventCategory eventCategory) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 20, 0);

        View cardView = createCardView(eventCategory);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCategorySelection(cardView, eventCategory);
            }
        });

        cardContainer.addView(cardView, params);
    }

    private View createCardView(EventCategory eventCategory) {
        LinearLayout cardView = new LinearLayout(context);
        cardView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        cardView.setOrientation(LinearLayout.HORIZONTAL);
        cardView.setGravity(Gravity.CENTER_VERTICAL);

        GradientDrawable shape = new GradientDrawable();
        shape.setColor(convertHexToIntUtil.change(eventCategory.getPrimaryColor()));
        shape.setCornerRadius(context.getResources().getDimension(R.dimen.card_corner_radius));
        cardView.setBackground(shape);

        ImageView ovalIcon = new ImageView(context);
        ovalIcon.setImageResource(R.drawable.eventcategoryicon);
        ovalIcon.setPadding(20, 0, 0, 0);
        int iconColor = convertHexToIntUtil.change(eventCategory.getIconColor());
        DrawableCompat.setTint(ovalIcon.getDrawable(), iconColor);
        ovalIcon.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        cardView.addView(ovalIcon);

        TextView textView = new TextView(context);
        textView.setText(eventCategory.getEventCategoryName());
        textView.setTextColor(Color.BLACK);
        textView.setPadding(16, 16, 16, 16);

        cardView.addView(textView);

        return cardView;
    }

    private void handleCategorySelection(View currentSelectedCard, EventCategory currentSelectedEventCategory) {
        if (lastSelectedEventCategoryCard != null && lastSelectedEventCategory != null) {
            lastSelectedEventCategoryCard.setBackgroundColor(convertHexToIntUtil.change(lastSelectedEventCategory.getPrimaryColor()));
        }
        currentSelectedCard.setBackgroundColor(convertHexToIntUtil.change(currentSelectedEventCategory.getSelectedColor()));

        lastSelectedEventCategoryCard = currentSelectedCard;
        lastSelectedEventCategory = currentSelectedEventCategory;
    }


    public void setUp() {
        TVDate = dialog.findViewById(R.id.TVDate);
        TVStartTime = dialog.findViewById(R.id.TVStartTime);
        TVEndTime = dialog.findViewById(R.id.TVEndTime);

        LinearLayout calendarClickable = dialog.findViewById(R.id.CalendarClickable);
        LinearLayout startTimeClickable = dialog.findViewById(R.id.StartTimeClickable);
        LinearLayout endTimeClickable = dialog.findViewById(R.id.EndTimeClickable);
        Button btnCreateEvent = dialog.findViewById(R.id.btnCreateEvent);
        TextView TVAddCategory = dialog.findViewById(R.id.TVAddCategory);
        cardContainer = dialog.findViewById(R.id.cardContainer);
        Set<EventCategory> convertedEventCategories = convertStringSetToEventCategories(eventCategories);

        for (EventCategory category : convertedEventCategories) {
            addCategory(category);
        }

        calendarClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDateIconClick();
            }
        });
        startTimeClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeCLick(view, TVStartTime, startTimeClickable);
            }
        });
        endTimeClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeCLick(view, TVEndTime, endTimeClickable);
            }
        });

        TVAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCategoryDialog();
            }

            private void showAddCategoryDialog() {
                showColorPickerUtil colorPickerUtil = new showColorPickerUtil(context);
                AlertDialog.Builder builder = colorPickerUtil.build(new showColorPickerUtil.ColorSelectedCallback() {
                    @Override
                    public void onCategoryAdded(EventCategory eventCategory) {
                        addCategory(eventCategory);
                        convertedEventCategories.add(eventCategory);
                        System.out.println("added event category" + eventCategory.getEventCategoryName());
                        editor.putStringSet(preference_constant.pEventCategories, convertEventCategoriesToStringSet(convertedEventCategories));
                        editor.commit();
                    }
                });
                builder.setTitle("Add New Category");

                builder.show();
            }
        });


        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = context.getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
                String userId = preferences.getString(preference_constant.pUserId, "");
                String eventId = UUID.randomUUID().toString();

                EditText ETEventName = dialog.findViewById(R.id.ETEventName);
                EditText ETNote = dialog.findViewById(R.id.ETNote);
                TextView TVStartTime = dialog.findViewById(R.id.TVStartTime);
                TextView TVEndTime = dialog.findViewById(R.id.TVEndTime);
                Switch remindsMe = dialog.findViewById(R.id.remindsMe);

                String eventName = ETEventName.getText().toString();
                String note = ETNote.getText().toString();
                String date = TVDate.getText().toString();
                String startTime = TVStartTime.getText().toString();
                String endTime = TVEndTime.getText().toString();
                String category = lastSelectedEventCategory != null ? lastSelectedEventCategory.getEventCategoryName() : "";

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (remindsMe.isChecked()) {
                        if (!alarmManager.canScheduleExactAlarms()) {
                            Toast.makeText(context, "Please grant permission to set alarm", Toast.LENGTH_LONG).show();
                            context.startActivity(new Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, Uri.parse("package:" + context.getPackageName())));
                            return;
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            int notificationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS);
                            if (notificationPermission != PackageManager.PERMISSION_GRANTED) {
                                Intent intent = new Intent();
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
                                context.startActivity(intent);
                                return;
                            }
                        }
                    }
                }

                if (eventName.isEmpty() || date.isEmpty() || startTime.equals("Start Time") || endTime.equals("End Time") || category.isEmpty()) {
                    showRequireFieldsDialog();
                } else {
                    String iconColor = lastSelectedEventCategory.getIconColor();
                    Event event = new Event(eventId, eventName, selectedStartTime.getTime(), selectedEndTime.getTime(), category, userId);
                    event.setEventNote(note);
                    event.setIconColor(iconColor);
                    impl.createEventInfo(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if (remindsMe.isChecked()) {
                                createNotificationChannel();
                                Intent intent = new Intent(context, NotificationReceiver.class);
                                intent.putExtra("title", eventName);
                                intent.putExtra("message", note);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, selectedStartTime.getTime(), pendingIntent);
                                Toast.makeText(context, "Scheduled", Toast.LENGTH_LONG).show();
                            }

                            AlertDialogUtil alertDialogUtil = new AlertDialogUtil(context, "Add event to your calendar?");
                            alertDialogUtil.showAlertDialog(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Calendar cal = Calendar.getInstance();
                                    Intent intent = new Intent(Intent.ACTION_EDIT);
                                    intent.setData(CalendarContract.Events.CONTENT_URI);
                                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.getEventStartTime());
                                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.getEventEndTime());
                                    intent.putExtra(CalendarContract.Events.TITLE, event.getEventName());
                                    context.startActivity(intent);
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                        }
                    });

                    dialog.dismiss();
                }
            }

        });

    }

    private void createNotificationChannel() {
        CharSequence name = "eventNotification";
        String description = "Notification for event";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(notification_constant.EVENT_CHANNEL_ID, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void onTimeCLick(View v, TextView TVTime, LinearLayout clickableTime) {
        Calendar currentTime = Calendar.getInstance();
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Calendar selectedTime = Calendar.getInstance();
                        selectedTime.setTime(new Date(selectedDate.getTime()));
                        selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        selectedTime.set(Calendar.MINUTE, selectedMinute);


                        if (clickableTime.getId() == R.id.StartTimeClickable) {
                            selectedStartTime = selectedTime.getTime();
                            String currentTimeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(selectedStartTime);
                            TVStartTime.setText(currentTimeString);
                        } else if (clickableTime.getId() == R.id.EndTimeClickable) {
                            selectedEndTime = selectedTime.getTime();
                            String currentTimeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(selectedEndTime);
                            TVEndTime.setText(currentTimeString);
                        }
                    }
                },
                currentHour,
                currentMinute,
                false
        );

        timePickerDialog.show();
    }

    public void onDateIconClick() {

        Calendar currentDate = Calendar.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Event Date");
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, this,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showRequireFieldsDialog() {
        Toast.makeText(context, "Please fill in appropriate event name, date, start time, end time and select a category", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        updateUIWithSelectedDate(c, dialog.findViewById(R.id.TVDate));
        selectedDate = c.getTime();
    }

    private void updateUIWithSelectedDate(Calendar selectedDate, View dialogView) {
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(selectedDate.getTime());
        TVDate.setText(currentDateString);
    }
}
