package com.example.bellyfull.modules.PersonalisedNotifications;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.MainActivity;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.repository.eventRepositoryImpl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class InputBottomSheet implements DatePickerDialog.OnDateSetListener {
    Dialog dialog;
    private List<RadioButton> radioButtons;
    private RadioGroup radioGroup;
    private TextView TVDate;
    private TextView TVStartTime;
    private TextView TVEndTime;
    private View overlayView;
    private Context context;
    eventRepositoryImpl impl;

    public InputBottomSheet(Dialog dialog) {
        this.dialog = dialog;
        this.context = dialog.getContext();
        impl = new eventRepositoryImpl(dialog.getContext());
    }

    public void setUp() {
        TVDate = dialog.findViewById(R.id.TVDate);
        TVStartTime = dialog.findViewById(R.id.TVStartTime);
        TVEndTime = dialog.findViewById(R.id.TVEndTime);

        ImageView ivCalendar = dialog.findViewById(R.id.IVCalendar);
        ImageView ivStartTime = dialog.findViewById(R.id.IVStartTime);
        ImageView ivEndTime = dialog.findViewById(R.id.IVEndTime);
        Button btnCreateEvent = dialog.findViewById(R.id.btnCreateEvent);
        TextView TVAddCategory = dialog.findViewById(R.id.TVAddCategory);
        RadioButton RB2 = dialog.findViewById(R.id.RB2);
        RadioButton RB3 = dialog.findViewById(R.id.RB3);
        RadioButton RB4 = dialog.findViewById(R.id.RB4);
        RadioButton RB5 = dialog.findViewById(R.id.RB5);

        radioButtons = new ArrayList<>();
        radioGroup = dialog.findViewById(R.id.MyRG);

        ivCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDateIconClick();
            }
        });
        ivStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeCLick(view, TVStartTime, ivStartTime);
            }
        });
        ivEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeCLick(view, TVEndTime, ivEndTime);
            }
        });

        // TODO: figure out a better implementation
        TVAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCategoryDialog();
            }

            private void showAddCategoryDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add New Category");

                final EditText input = new EditText(context);
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
                if (radioButtons.size() == 0) {
                    RB2.setText(categoryText);
                    RB2.setVisibility(View.VISIBLE);
                    radioButtons.add(RB2);
                } else if (radioButtons.size() == 1) {
                    RB3.setText(categoryText);
                    RB3.setVisibility(View.VISIBLE);
                    radioButtons.add(RB3);
                } else if (radioButtons.size() == 2) {
                    RB4.setText(categoryText);
                    RB4.setVisibility(View.VISIBLE);
                    radioButtons.add(RB4);
                } else if (radioButtons.size() == 3) {
                    RB5.setText(categoryText);
                    RB5.setVisibility(View.VISIBLE);
                    radioButtons.add(RB5);
                } else {
                    Toast.makeText(context, "Maximum number of categories reached", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = context.getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
                String userId = preferences.getString(preference_constant.pUserId, "");
                String eventId = UUID.randomUUID().toString();
                impl.createEventInfo(userId, eventId);

                EditText ETEventName = dialog.findViewById(R.id.ETEventName);
                EditText ETNote = dialog.findViewById(R.id.ETNote);
                TextView TVDate = dialog.findViewById(R.id.TVDate);
                TextView TVStartTime = dialog.findViewById(R.id.TVStartTime);
                TextView TVEndTime = dialog.findViewById(R.id.TVEndTime);
                RadioButton selectedRB = dialog.findViewById(radioGroup.getCheckedRadioButtonId());

                if (ETEventName.getText().toString().isEmpty() || TVDate.getText().toString().isEmpty()) {
                    showRequireFieldsDialog();
                } else {
                    String eventName = ETEventName.getText().toString();
                    String note = ETNote.getText().toString();
                    String date = TVDate.getText().toString();
                    String starttime = TVStartTime.getText().toString();
                    String endtime = TVEndTime.getText().toString();
                    String category;
                    if (selectedRB != null) {
                        category = selectedRB.getText().toString();
                    } else
                        category = null;

                    impl.setEventInfoEventName(eventId, eventName);
                    impl.setEventInfoNote(eventId, note);
                    impl.setEventInfoDate(eventId, date);
                    if (!starttime.equals("Start Time")) {
                        impl.setEventInfoStartTime(eventId, starttime);
                    } else
                        impl.setEventInfoStartTime(eventId, null);
                    if (!endtime.equals("End Time")) {
                        impl.setEventInfoEndTime(eventId, endtime);
                    } else
                        impl.setEventInfoEndTime(eventId, null);
                    impl.setEventInfoCategory(eventId, category);

                    dialog.dismiss();
                }
            }

        });

    }

    private void onTimeCLick(View v, TextView TVTime, ImageView IVTime) {
        Calendar currentTime = Calendar.getInstance();
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Calendar selectedTime = Calendar.getInstance();
                        selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        selectedTime.set(Calendar.MINUTE, selectedMinute);

                        if (IVTime.getId() == R.id.IVStartTime) {
                            String currentTimeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(selectedTime.getTime());
                            TVStartTime.setText(currentTimeString);
                        } else if (IVTime.getId() == R.id.IVEndTime) {
                            String currentTimeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(selectedTime.getTime());
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

    private void onDateIconClick() {

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
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage("Please enter the required fields.");
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        updateUIWithSelectedDate(c, dialog.findViewById(R.id.TVDate));
    }

    private void updateUIWithSelectedDate(Calendar selectedDate, View dialogView) {
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(selectedDate.getTime());
        TVDate.setText(currentDateString);
    }
}
