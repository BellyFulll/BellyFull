package com.example.bellyfull.modules.PersonalisedNotifications.Fragments;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.collection.Event;
import com.example.bellyfull.data.firebase.ports.eventRepository;
import com.example.bellyfull.data.firebase.repository.eventRepositoryImpl;
import com.example.bellyfull.modules.PersonalisedNotifications.Activity.NotificationSettingsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class EventRepository {
    private FirebaseFirestore db;

    public EventRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void addEvent(Event event) {
        // Add logic to add an event to Firebase
        db.collection("events")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Event added successfully
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                    }
                });
    }
}

public class CalendarFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private TextView TVDate;
    private TextView TVStartTime;
    private TextView TVEndTime;
    private static final String CHANNEL_ID = "ReminderChannel";

//    public CalendarFragment() {
//        super(R.layout.fragment_calendar);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        TVDate = view.findViewById(R.id.TVDate);
        TVStartTime = view.findViewById(R.id.TVStartTime);
        TVEndTime = view.findViewById(R.id.TVEndTime);

        FloatingActionButton addEvent = getView().findViewById(R.id.btnAddEvent);
        Button settingsButton = view.findViewById(R.id.settingsButton);
        Context context = getActivity();
        eventRepository impl = new eventRepositoryImpl(context);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_event_input, null);
                showEventInputDialog(dialogView);
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
    private List<RadioButton> radioButtons;
    private RadioGroup radioGroup;
    private void showEventInputDialog(View dialogView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Event");
        builder.setView(dialogView);

        this.TVDate = dialogView.findViewById(R.id.TVDate);
        this.TVEndTime=dialogView.findViewById(R.id.TVEndTime);
        this.TVStartTime=dialogView.findViewById(R.id.TVStartTime);

        ImageView ivCalendar = dialogView.findViewById(R.id.IVCalendar);
        ImageView ivStartTime = dialogView.findViewById(R.id.IVStartTime);
        ImageView ivEndTime = dialogView.findViewById(R.id.IVEndTime);
        Button btnCreateEvent = dialogView.findViewById(R.id.btnCreateEvent);
        TextView TVAddCategory = dialogView.findViewById(R.id.TVAddCategory);
        RadioButton RB2 = dialogView.findViewById(R.id.RB2);
        RadioButton RB3 = dialogView.findViewById(R.id.RB3);
        RadioButton RB4 = dialogView.findViewById(R.id.RB4);
        RadioButton RB5 = dialogView.findViewById(R.id.RB5);


        radioButtons = new ArrayList<>();
        radioGroup = dialogView.findViewById(R.id.MyRG);
        int[] colors = {R.color.black, R.color.purple, R.color.grey, R.color.brown1, R.color.pink2};

        ivCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDateIconClick(view, TVDate);
            }
        });

        ivStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeCLick(view,TVStartTime,ivStartTime);
            }
        });
        ivEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeCLick(view,TVEndTime,ivEndTime);
            }
        });

        TVAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCategoryDialog();
            }
            private void showAddCategoryDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Add New Category");

                final EditText input = new EditText(requireContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String categoryText = input.getText().toString();
                        onAddNewCategoryClick(categoryText);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
            private void onAddNewCategoryClick(String categoryText) {
                if (radioButtons.size() ==0 ) {
                    RB2.setText(categoryText);
                    RB2.setVisibility(View.VISIBLE);
                    radioButtons.add(RB2);
                } else if (radioButtons.size() ==1){
                    RB3.setText(categoryText);
                    RB3.setVisibility(View.VISIBLE);
                    radioButtons.add(RB3);
                }else if (radioButtons.size() ==2) {
                    RB4.setText(categoryText);
                    RB4.setVisibility(View.VISIBLE);
                    radioButtons.add(RB4);
                }else if (radioButtons.size() ==3) {
                    RB5.setText(categoryText);
                    RB5.setVisibility(View.VISIBLE);
                    radioButtons.add(RB5);
                }
                else {
                    Toast.makeText(requireContext(), "Maximum number of categories reached", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCreateEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Event event = new Event();

                EditText ETEventName = dialogView.findViewById(R.id.ETEventName);
                EditText ETNote = dialogView.findViewById(R.id.ETNote);
                TextView TVDate = dialogView.findViewById(R.id.TVDate);
                TextView TVStartTime = dialogView.findViewById(R.id.TVStartTime);
                TextView TVEndTime = dialogView.findViewById(R.id.TVEndTime);

                event.setEventName(ETEventName.getText().toString());
                event.setNote(ETNote.getText().toString());
                event.setDate(TVDate.getText().toString());
                event.setStartTime(TVStartTime.getText().toString());
                event.setEndTime(TVEndTime.getText().toString());

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if(selectedRadioButtonId != -1){
                    RadioButton selectedRB= dialogView.findViewById(selectedRadioButtonId);
                    event.setCategory(selectedRB.getText().toString());
                    event.setCategoryColor(Color.valueOf(selectedRB.getCurrentHintTextColor()));
                }
                if (ETEventName.getText().toString().isEmpty() || TVDate.getText().toString().isEmpty()) {
                    showRequireFieldsDialog();
                }else {
                    saveEventToFirebase(event);
                    builder.create().dismiss();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }



    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR , year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        updateUIWithSelectedDate(c,getView().findViewById(R.id.TVDate));
    }

    private void onDateIconClick(View view, TextView TVDate) {

        Calendar currentDate = Calendar.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose Event Date");
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), this,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


    private void updateUIWithSelectedDate(Calendar selectedDate, View dialogView) {
            String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(selectedDate.getTime());
                TVDate.setText(currentDateString);
    }

    private void onTimeCLick(View v, TextView TVTime, ImageView IVTime){
        Calendar currentTime = Calendar.getInstance();
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Calendar selectedTime = Calendar.getInstance();
                        selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        selectedTime.set(Calendar.MINUTE, selectedMinute);

                        if(IVTime.getId() == R.id.IVStartTime){
                            String currentTimeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(selectedTime.getTime());
                            TVStartTime.setText(currentTimeString);
                        }else if (IVTime.getId() == R.id.IVEndTime) {
                            String currentTimeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(selectedTime.getTime());
                            TVEndTime.setText(currentTimeString);
                        }
                    }
                },
                currentHour,
                currentMinute,
                false // 24-hour format
        );

        timePickerDialog.show();
    }


    private void showRequireFieldsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Error");
        builder.setMessage("Please enter the required fields.");
        builder.setPositiveButton("OK", null); // You can add a listener if needed
        builder.create().show();
    }
    private void saveEventToFirebase(Event event) {
        EventRepository repository = new EventRepository();
        repository.addEvent(event);
    }


    //    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        Button settingsButton = view.findViewById(R.id.settingsButton);
//        ImageView ivCalendar = view.findViewById(R.id.IVCalendar);
//        Context context = getActivity();
//        eventRepository impl = new eventRepositoryImpl(context);
//        settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onSettingsButtonClick();
//            }
//        });
//        // Schedule health checkup reminder when the fragment is created
//        scheduleHealthCheckupReminder();
//
//        ivCalendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onIVCalendarClick(view);
//            }
//        });
//        CalendarView calendarView = view.findViewById(R.id.calendarView);
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
//
//            }
//        });
//
//        FloatingActionButton addEvent = getView().findViewById(R.id.btnAddEvent);
//        addEvent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_event_input, null);
//                showEventInputDialog();
//            }
//        });
//    }
//    public void onIVCalendarClick(View view) {
//        showDatePickerDialog();
//    }
//    private void showDatePickerDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setTitle("Choose Event Date");
//        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        // Handle the selected date
//                        // You can use the selected date to update UI or perform other actions
//                    }
//                },
//                // Set the initial date in the DatePicker dialog (you can set it to the current date)
//                Calendar.getInstance().get(Calendar.YEAR),
//                Calendar.getInstance().get(Calendar.MONTH),
//                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
//        );
//        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_date_picker, null);
//        builder.setView(dialogView);
//
//        datePickerDialog.show();
//    }
//
//    private void showEventInputDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setTitle("Add New Event");
//
//        // Inflate the layout for the dialog
//        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_event_input, null);
//        builder.setView(dialogView);
//
//        // Initialize the DatePicker in the dialog
//        DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
////        datePicker.init(year, month, dayOfMonth, null);
//
//        // Set up the buttons
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                int selectedYear = datePicker.getYear();
//                int selectedMonth = datePicker.getMonth();
//                int selectedDayOfMonth = datePicker.getDayOfMonth();
//                // Retrieve user input and save the event
//                // Update the UI to display the event below the calendar
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//                dialog.dismiss();
//            }
//        });
//
//        builder.create().show();
//    }
//
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
//
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
