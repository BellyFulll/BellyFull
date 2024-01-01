package com.example.bellyfull.modules.General;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.R;
import com.example.bellyfull.modules.HomeFragmentDirections;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button BtnBackProfile = view.findViewById(R.id.BtnBackProfile);


        BtnBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ProfileFragmentDirections.actionProfileFragmentToHomeFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
}
