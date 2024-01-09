package com.example.bellyfull.modules.PersonalisedNotifications.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.repository.eventRepositoryImpl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


public class EventInputFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    public EventInputFragment() {
        super(R.layout.fragment_event_input);
    }
    private List<RadioButton> radioButtons;
    private RadioGroup radioGroup;
    private TextView TVDate;
    private TextView TVStartTime;
    private TextView TVEndTime;
    private View overlayView;
    private CalendarFragment calendarFragment;
    eventRepositoryImpl impl;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        impl = new eventRepositoryImpl(getContext());
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TVDate = view.findViewById(R.id.TVDate);
        TVStartTime = view.findViewById(R.id.TVStartTime);
        TVEndTime = view.findViewById(R.id.TVEndTime);
//        overlayView = calendarFragment.getView().findViewById(R.id.overlayView);
        calendarFragment = new CalendarFragment();
//        overlayView = calendarFragment.getOverlayView();

        ImageView ivCalendar = view.findViewById(R.id.IVCalendar);
        ImageView ivStartTime = view.findViewById(R.id.IVStartTime);
        ImageView ivEndTime = view.findViewById(R.id.IVEndTime);
        Button btnCreateEvent = view.findViewById(R.id.btnCreateEvent);
        TextView TVAddCategory = view.findViewById(R.id.TVAddCategory);
        RadioButton RB2 = view.findViewById(R.id.RB2);
        RadioButton RB3 = view.findViewById(R.id.RB3);
        RadioButton RB4 = view.findViewById(R.id.RB4);
        RadioButton RB5 = view.findViewById(R.id.RB5);

        radioButtons = new ArrayList<>();
        radioGroup = view.findViewById(R.id.MyRG);

        ivCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDateIconClick(view, TVDate);
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
                    Toast.makeText(requireContext(), "Maximum number of categories reached", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
                String userId = preferences.getString(preference_constant.pUserId, "");
                String eventId = UUID.randomUUID().toString();
                impl.createEventInfo(userId, eventId);

                EditText ETEventName = getView().findViewById(R.id.ETEventName);
                EditText ETNote = getView().findViewById(R.id.ETNote);
                TextView TVDate = getView().findViewById(R.id.TVDate);
                TextView TVStartTime = getView().findViewById(R.id.TVStartTime);
                TextView TVEndTime = getView().findViewById(R.id.TVEndTime);
                RadioButton selectedRB = getView().findViewById(radioGroup.getCheckedRadioButtonId());
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

                    closeEventInputFragment();
                }
            }
            private void closeEventInputFragment() {
                if (overlayView != null) {
                    overlayView.setVisibility(View.GONE);
                } //I CANNOT ACCESS OVERLAYVIEWWWWWWWWWWWWWWW
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = fragmentManager.findFragmentById(R.id.FCVforInputEvent);

                if (fragment != null) {
                    transaction.remove(fragment);
                    transaction.commitAllowingStateLoss();
                }
            }

        });

    }
    private void onTimeCLick(View v, TextView TVTime, ImageView IVTime) {
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
    private void showRequireFieldsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Error");
        builder.setMessage("Please enter the required fields.");
        builder.setPositiveButton("OK", null); // You can add a listener if needed
        builder.create().show();
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        updateUIWithSelectedDate(c, getView().findViewById(R.id.TVDate));
    }
    private void updateUIWithSelectedDate(Calendar selectedDate, View dialogView) {
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(selectedDate.getTime());
        TVDate.setText(currentDateString);
    }
}