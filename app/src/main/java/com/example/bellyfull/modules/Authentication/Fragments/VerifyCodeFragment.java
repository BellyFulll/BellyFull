package com.example.bellyfull.modules.Authentication.Fragments;

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
        VerifyCodeFragmentArgs args = VerifyCodeFragmentArgs.fromBundle(getArguments());
        String email = args.getEmail();
        String verificationCode = args.getVerificationCode();

        btnVerifyCode.setOnClickListener(v -> {
            String verificationCodeIn = etVerificationCode.getText().toString().trim();
            if (!verificationCodeIn.isEmpty()) {
                // Validate the verification code
                if (verificationCodeIn.equals(verificationCode)) {
                    navigateToResetPassword(email);
                } else {
                    Toast.makeText(requireContext(), "Invalid verification code", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Enter the verification code", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToResetPassword(String email) {
        NavDirections action = VerifyCodeFragmentDirections
                .actionVerifyCodeFragmentToResetPasswordFragment(email);
        Navigation.findNavController(requireView()).navigate(action);
    }
}

