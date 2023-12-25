package com.example.bellyfull.modules.Authentication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bellyfull.MainActivity;
import com.example.bellyfull.data.firebase.repository.fbLoginRepositoryImpl;
import com.example.bellyfull.data.firebase.ports.dbLoginCallback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.Constant.preference_constant;

import com.example.bellyfull.R;
import com.example.bellyfull.utils.TextValidator;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Variables
        fbLoginRepositoryImpl impl = new fbLoginRepositoryImpl();
        SharedPreferences preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = preferences.edit();
        Button BtnLogin = view.findViewById(R.id.BtnLogin);
        Button BtnLoginSignup = view.findViewById(R.id.BtnLoginSignup);
        EditText ETEmail = view.findViewById(R.id.ETLoginEmail);
        EditText ETPassword = view.findViewById(R.id.ETLoginPassword);

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
                String email = ETEmail.getText().toString();
                String inputPassword = ETPassword.getText().toString();
                boolean v1 = TextValidator.validateEmail(ETEmail, email);
                boolean v2 = TextValidator.validatePassword(ETPassword, inputPassword);
                if (!v1 || !v2) return;

                impl.getPassword(email, new dbLoginCallback() {
                    @Override
                    public void onCallback(QueryDocumentSnapshot document) {
                        System.out.println(document.getString("password"));
                        if (inputPassword.equals(document.getString("password"))) {
                            String userId = document.getString("userId");
                            spEditor.putBoolean(preference_constant.pIsLogin, true);
                            spEditor.putString(preference_constant.pUserId, userId);
                            spEditor.commit();

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "Invalid Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
