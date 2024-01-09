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
import com.example.bellyfull.data.firebase.repository.fbProfileRepositoryImpl;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

public class ForgotPwdFragment extends Fragment {
    private FirebaseFirestore db;
    EmailSender emailSender = new EmailSender();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgotpwd, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();

        EditText etEmail = view.findViewById(R.id.EFPwdEmail);
        Button btnSendVerification = view.findViewById(R.id.BtnFPwd);

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
        fbProfileRepositoryImpl impl = new fbProfileRepositoryImpl(getContext());

        db.collection("USER")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot document = task.getResult();
                        // Email exists, send verification code
                        String verificationCode = generateVerificationCode();
                        // Send email with verification code using your preferred method (e.g., email service)
                        emailSender.sendEmail(email, verificationCode);
                        Toast.makeText(requireContext(), "Verification code sent", Toast.LENGTH_SHORT).show();
                        // Now, navigate to the verification code input page
                        NavDirections action = ForgotPwdFragmentDirections.actionForgotPwdFragmentToVerifyCodeFragment(email, verificationCode);
                        Navigation.findNavController(requireView()).navigate(action);
                    } else {
                        // Email does not exist
                        Toast.makeText(requireContext(), "Email does not exist", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(999999);
        String codeStr = String.valueOf(code);
        codeStr = String.format("%06d", code);
        return codeStr;
    }
}

