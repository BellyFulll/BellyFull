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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class ForgotPwdFragment extends Fragment {
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgotpwd, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();

        EditText etEmail = view.findViewById(R.id.etForgotPwdEmail);
        Button btnSendVerification = view.findViewById(R.id.btnSendVerification);

        btnSendVerification.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if (!email.isEmpty()) {
                sendVerificationCode(email);
            } else {
                Toast.makeText(requireContext(), "Enter your email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendVerificationCode(String email) {
        db.collection("users").document(email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Email exists, send verification code
                            String verificationCode = generateVerificationCode();
                            // Send email with verification code using your preferred method (e.g., email service)
                            // Now, navigate to the verification code input page
                            NavDirections action = ForgotPwdFragmentDirections.actionForgotPwdFragmentToVerifyCodeFragment(email, verificationCode);
                            Navigation.findNavController(requireView()).navigate(action);
                        } else {
                            Toast.makeText(requireContext(), "Email does not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Failed to check email existence", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String generateVerificationCode() {
        Random random = new Random();
        // generate 6 digit random number
        int code = random.nextInt(999999);
        // parse to string
        String codeStr = String.valueOf(code);
        // pad with leading zeros
        codeStr = String.format("%06d", code);
        return codeStr;
    }
}

