package com.example.bellyfull.modules.Authentication.Fragments;

import android.os.Build;
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

import com.example.bellyfull.data.firebase.repository.fbProfileRepositoryImpl;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.bellyfull.R;

public class ResetPasswordFragment extends Fragment {
    private FirebaseFirestore db;
    private String userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve the email from arguments
        ResetPasswordFragmentArgs args = ResetPasswordFragmentArgs.fromBundle(getArguments());
        userEmail = args.getEmail();

        EditText etNewPassword = view.findViewById(R.id.etNewPassword);
        Button btnResetPassword = view.findViewById(R.id.btnResetPassword);
        btnResetPassword.setOnClickListener(v -> {
            String newPassword = etNewPassword.getText().toString().trim();
            if (!newPassword.isEmpty() && newPassword.length() >= 8) {
                resetPassword(newPassword);
                // Navigate to the login page after resetting the password
                Navigation.findNavController(requireView()).popBackStack(R.id.loginFragment, false);
            } else {
                Toast.makeText(requireContext(), "Enter a new password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPassword(String newPassword) {
        // Reset the password
        fbProfileRepositoryImpl impl = new fbProfileRepositoryImpl(getContext());
        impl.updatePassword(userEmail, newPassword);
    }
}
