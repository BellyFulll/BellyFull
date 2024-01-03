package com.example.bellyfull;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.NHFMain);
        NavController navController = navHostFragment.getNavController();
        setupBottomNavMenu(navController);
        BottomNavigationView navBar = findViewById(R.id.bottomNavigationView);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (
                        navDestination.getId() == R.id.profileFragment || navDestination.getId() == R.id.profileEditFragment
                ) {
                    navBar.setVisibility(View.INVISIBLE);

                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone((ConstraintLayout) findViewById(R.id.CLMainActivity));
                    constraintSet.connect(R.id.NHFMain, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
                    constraintSet.connect(R.id.NHFMain, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
                    constraintSet.applyTo(findViewById(R.id.CLMainActivity));
                } else {
                    navBar.setVisibility(View.VISIBLE);

                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone((ConstraintLayout) findViewById(R.id.CLMainActivity));
                    constraintSet.connect(R.id.NHFMain, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
                    constraintSet.connect(R.id.NHFMain, ConstraintSet.BOTTOM, R.id.bottomNavigationView, ConstraintSet.TOP, 0);
                    constraintSet.applyTo(findViewById(R.id.CLMainActivity));
                }
            }
        });
    }
        @Override
    public void onBackPressed() {
        showExitConfirmationDialog();
    }

    private void setupBottomNavMenu(NavController navController) {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }


    private void showExitConfirmationDialog() {
        // Create a custom dialog with your layout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.exit_dialog, null);
        builder.setView(dialogView);

        Button confirmButton = dialogView.findViewById(R.id.confirmButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        // Set click listeners for buttons
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and all activities below it in the stack
                finishAffinity();
                Log.i(TAG, "finishAffinity() called");
                alertDialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User clicked the "No" button, dismiss the dialog
                alertDialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        alertDialog = builder.create();
        alertDialog.show();
    }
}