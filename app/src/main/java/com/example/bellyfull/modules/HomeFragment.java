package com.example.bellyfull.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.R;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton IBProfileCTA = view.findViewById(R.id.IBProfileCTA);
        ImageButton IBBabyCTA = view.findViewById(R.id.IBBabyCTA);
        ImageButton IBMumCTA = view.findViewById(R.id.IBMumCTA);


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
}