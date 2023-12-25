package com.example.bellyfull.modules.Authentication.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.R;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Variables
        Button BtnLogin = view.findViewById(R.id.BtnLogin);
        Button BtnLoginSignup = view.findViewById(R.id.BtnLoginSignup);

        // Listeners
        BtnLoginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ETEmail = view.findViewById(R.id.ETLoginEmail);
                EditText ETPassword = view.findViewById(R.id.ETLoginPassword);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
