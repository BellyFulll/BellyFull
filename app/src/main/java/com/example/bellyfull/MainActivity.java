package com.example.bellyfull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.NHFMain);
        NavController navController = navHostFragment.getNavController();
        setupBottomNavMenu(navController);
    }

    private void setupBottomNavMenu(NavController navController) {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}