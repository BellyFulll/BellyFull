package com.example.bellyfull.modules.Authentication.Fragments;

// VerifyCodeFragment.java

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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.R;

public class VerifyCodeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_verify_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText etVerificationCode = view.findViewById(R.id.etVerificationCode);
        Button btnVerifyCode = view.findViewById(R.id.btnVerifyCode);

        btnVerifyCode.setOnClickListener(v -> {
            String verificationCode = etVerificationCode.getText().toString().trim();
            if (!verificationCode.isEmpty()) {
                // Validate the verification code
                if (verificationCode.equals("123456")) { // Replace with your logic to validate the code
                    // Verification successful, navigate to the password reset page
                    NavDirections action = VerifyCodeFragmentDirections.actionVerifyCodeFragmentToResetPasswordFragment();
                    Navigation.findNavController(requireView()).navigate(action);
                } else {
                    Toast.makeText(requireContext(), "Invalid verification code", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Enter the verification code", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

