package com.example.bellyfull.modules.Authentication.Fragments;

// ResetPasswordFragment.java

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bellyfull.R;

public class ResetPasswordFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText etNewPassword = view.findViewById(R.id.etNewPassword);
        Button btnResetPassword = view.findViewById(R.id.btnResetPassword);

        btnResetPassword.setOnClickListener(v -> {
            String newPassword = etNewPassword.getText().toString().trim();
            if (!newPassword.isEmpty()) {
                // Implement your logic to update the user's password in Firestore
                // This may involve updating the 'password' field in the user document
                // Navigate to the login page after resetting the password
                Navigation.findNavController(requireView()).popBackStack(R.id.loginFragment, false);
            } else {
                Toast.makeText(requireContext(), "Enter a new password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
