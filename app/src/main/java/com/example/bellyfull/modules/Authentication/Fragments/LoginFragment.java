package com.example.bellyfull.modules.Authentication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.MainActivity;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.ports.dbLoginCallback;
import com.example.bellyfull.data.firebase.repository.fbLoginRepositoryImpl;
import com.example.bellyfull.data.firebase.repository.fbSignUpRepositoryImpl;
import com.example.bellyfull.utils.TextValidator;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LoginFragment extends Fragment {
    private static final int RC_SIGN_IN = 9001;
    private FirebaseFirestore db;
    private GoogleSignInClient mGoogleSignInClient;
    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);

        // Variables
        fbLoginRepositoryImpl impl = new fbLoginRepositoryImpl();
        SharedPreferences preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = preferences.edit();
        Button BtnLogin = view.findViewById(R.id.BtnLogin);
        Button BtnLoginSignup = view.findViewById(R.id.BtnLoginSignup);
        Button BtnLoginGoogleSignup = view.findViewById(R.id.BtnLoginGoogleSignup);
        Button BtnLoginForgotPwd = view.findViewById(R.id.BtnLoginForgotPass);
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

        BtnLoginGoogleSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });

        BtnLoginForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = LoginFragmentDirections.actionLoginFragmentToForgotPwdFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            handleGoogleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data));
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.i("Google Sign-In", "signInResult:success email=" + account.getEmail());
            // Check for existing user with the same email
            db.collection("USER")
                .whereEqualTo("email", account.getEmail())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // User already exists, proceed to login
                        fbLoginRepositoryImpl impl = new fbLoginRepositoryImpl();
                        impl.getPassword(account.getEmail(), new dbLoginCallback() {
                            @Override
                            public void onCallback(QueryDocumentSnapshot document) {
                                String userId = document.getString("userId");
                                SharedPreferences preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
                                SharedPreferences.Editor spEditor = preferences.edit();
                                spEditor.putBoolean(preference_constant.pIsLogin, true);
                                spEditor.putString(preference_constant.pUserId, userId);
                                spEditor.commit();
                            }
                        });
                        Toast.makeText(requireContext(), "Google Sign-In success", Toast.LENGTH_SHORT).show();
                    } else {
                        // User doesn't exist, proceed with registration
                        fbSignUpRepositoryImpl impl = new fbSignUpRepositoryImpl(requireContext());
                        impl.registerUser(account.getDisplayName(), account.getEmail(), null);

                        fbLoginRepositoryImpl impl2 = new fbLoginRepositoryImpl();
                        impl2.getPassword(account.getEmail(), new dbLoginCallback() {
                            @Override
                            public void onCallback(QueryDocumentSnapshot document) {
                                String userId = document.getString("userId");
                                SharedPreferences preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
                                SharedPreferences.Editor spEditor = preferences.edit();
                                spEditor.putBoolean(preference_constant.pIsLogin, true);
                                spEditor.putString(preference_constant.pUserId, userId);
                                spEditor.commit();
                            }
                        });
                        Toast.makeText(requireContext(), "Google Sign-Up success", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                });
            } catch (ApiException e) {
                Log.e("Google Sign-In", "signInResult:failed code=" + e.getStatusCode());
                Log.e("Google Sign-In", "signInResult:failed message=" + e);
                Toast.makeText(requireContext(), "Google Sign-In failed", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
