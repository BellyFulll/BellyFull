package com.example.bellyfull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bellyfull.modules.General.MainLoadingFragmentDirections;

public class PreLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);

        // variables
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        boolean ISLOGIN = sharedPreferences.getBoolean("isLogin", false);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.NHFPreLogin);
        NavController navController = navHostFragment.getNavController();

        // logic
        if (ISLOGIN) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        navController.navigate(MainLoadingFragmentDirections.actionMainLoadingFragmentToLoginFragment());
    }
}