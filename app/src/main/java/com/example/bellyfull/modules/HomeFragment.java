package com.example.bellyfull.modules;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.collection.Event;
import com.example.bellyfull.data.firebase.repository.eventRepositoryImpl;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    private eventRepositoryImpl impl;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        impl = new eventRepositoryImpl(getContext());

        ImageButton IBProfileCTA = view.findViewById(R.id.IBProfileCTA);
        ImageButton IBBabyCTA = view.findViewById(R.id.IBBabyCTA);
        ImageButton IBMumCTA = view.findViewById(R.id.IBMumCTA);

        retrieveAndDisplayEvents(new Date());


        IBProfileCTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = HomeFragmentDirections.actionHomeFragmentToProfileFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        IBBabyCTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = HomeFragmentDirections.actionHomeFragmentToBabyInputFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        IBMumCTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = HomeFragmentDirections.actionHomeFragmentToMomInputFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
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
        LinearLayout eventContainer = getView().findViewById(R.id.homeEventContainer);
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
}