package com.example.bellyfull.modules.General;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.R;

public class ProfileEditFragment extends Fragment {

    public ProfileEditFragment() {
        super(R.layout.fragment_edit_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button BtnBackProfileEdit = view.findViewById(R.id.BtnBackProfileEdit);

        BtnBackProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ProfileEditFragmentDirections.actionProfileEditFragmentToProfileFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
}
