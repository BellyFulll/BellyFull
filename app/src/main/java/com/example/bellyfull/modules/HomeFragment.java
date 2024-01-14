package com.example.bellyfull.modules;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.collection.BabyInfo;
import com.example.bellyfull.data.firebase.collection.Event;
import com.example.bellyfull.data.firebase.collection.User;
import com.example.bellyfull.data.firebase.ports.dbBabyInfoCallback;
import com.example.bellyfull.data.firebase.ports.dbProfileCallback;
import com.example.bellyfull.data.firebase.repository.dbBabyInfoRepositoryImpl;
import com.example.bellyfull.data.firebase.repository.eventRepositoryImpl;
import com.example.bellyfull.data.firebase.repository.fbProfileRepositoryImpl;
import com.example.bellyfull.utils.WeekCalculator;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    private eventRepositoryImpl impl;
    private String userId;
    private TextView TVWeeksPregnantValue;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    dbBabyInfoCallback callback = new dbBabyInfoCallback() {
        @Override
        public void onSuccess(BabyInfo babyInfo) {
            updateUI(babyInfo);
        }
    };

    dbProfileCallback profileCallback = new dbProfileCallback() {
        @Override
        public void onSuccess(User user) {
            updateProfileUI(user);
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
        userId = preferences.getString(preference_constant.pUserId, "");

        ImageButton IBProfileCTA = view.findViewById(R.id.IBProfileCTA);
        ImageButton IBBabyCTA = view.findViewById(R.id.IBBabyCTA);
        ImageButton IBMumCTA = view.findViewById(R.id.IBMumCTA);
        TVWeeksPregnantValue = view.findViewById(R.id.TVWeeksPregnantValue);

        impl = new eventRepositoryImpl(getContext());
        dbBabyInfoRepositoryImpl babyImpl = new dbBabyInfoRepositoryImpl(getContext());
        babyImpl.getBabyInfo(userId, callback);
        fbProfileRepositoryImpl impl = new fbProfileRepositoryImpl(getContext());
        impl.getUserDetails(userId, profileCallback);

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
            if (!event.getUserId().matches(userId)) {
                continue;
            }

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

    private void updateUI(BabyInfo babyInfo) {
        System.out.println(babyInfo);
        if (getView() == null) {
            return;
        }
        TextView TVBabyHeightValue = getView().findViewById(R.id.TVBabyHeightValue);
        TextView TVBabyWeightValue = getView().findViewById(R.id.TVBabyWeightValue);

        if (babyInfo != null) {
            Double Weight = babyInfo.getFetalWeight();
            Double Height = babyInfo.getFetalLength();

            if (Weight != null) {
                TVBabyWeightValue.setText(String.valueOf(Weight));
            }

            if (Height != null) {
                TVBabyHeightValue.setText(String.valueOf(Height));
            }

        }
    }

    private void updateProfileUI(User user) {
        if (user.getDateOfConception() != 0) {
            WeekCalculator wc = new WeekCalculator(user.getDateOfConception());
            int weeksOfDifference = wc.calculateWeeksDifference();
            TVWeeksPregnantValue.setText(String.valueOf(weeksOfDifference));
        }
    }
}