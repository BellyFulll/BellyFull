package com.example.bellyfull.modules.Authentication.Fragments;

import android.content.Context;
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
import com.example.bellyfull.data.firebase.repository.fbSignUpRepositoryImpl;
import com.example.bellyfull.utils.TextValidator;

public class RegisterFragment extends Fragment {
    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Variable
        Button BtnSignUp = view.findViewById(R.id.BtnSignUp);
        Button BtnSignUpLogin = view.findViewById(R.id.BtnSignUpLogin);
        EditText ETName = view.findViewById(R.id.ETSignUpName);
        EditText ETEmail = view.findViewById(R.id.ETSignUpEmail);
        EditText ETPassword = view.findViewById(R.id.ETSignUpPassword);
        Context context = getActivity();
        fbSignUpRepositoryImpl impl = new fbSignUpRepositoryImpl(context);

        // listeners
        BtnSignUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = ETName.getText().toString();
                String email = ETEmail.getText().toString();
                String password = ETPassword.getText().toString();
                boolean v1 = TextValidator.validateName(ETName, name);
                boolean v2 = TextValidator.validateEmail(ETEmail, email);
                boolean v3 = TextValidator.validatePassword(ETPassword, password);

                if (!v1 || !v2 || !v3) return;
                impl.registerUser(
                        ETName.getText().toString(),
                        ETEmail.getText().toString(),
                        ETPassword.getText().toString()
                );
                ETName.getText().clear();
                ETEmail.getText().clear();
                ETPassword.getText().clear();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("View destroyed in signup");
    }
}
