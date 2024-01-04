package com.example.bellyfull.modules.PregnancyTracking.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.R;

public class MomInputFragment extends Fragment {

    public MomInputFragment() {
        super(R.layout.fragment_mom_input);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnMomCancel = view.findViewById(R.id.btnMomCancel);


        btnMomCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = MomInputFragmentDirections.actionMomInputFragmentToHomeFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
}