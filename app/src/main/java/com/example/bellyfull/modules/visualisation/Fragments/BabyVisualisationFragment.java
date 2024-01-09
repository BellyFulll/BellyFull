package com.example.bellyfull.modules.visualisation.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bellyfull.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BabyVisualisationFragment extends Fragment {

    private DatabaseReference databaseReference;
    private TextView tvWeight;
    private TextView tvHeight;
    private TextView tvHeadCircumference;

    public BabyVisualisationFragment() {
        super(R.layout.fragment_baby_visualisation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(); // This points to the root node

        // Initialize TextViews
        tvWeight = view.findViewById(R.id.tvWeight);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvHeadCircumference = view.findViewById(R.id.tvHeadCircumference);

        // Manually set text for testing
        tvWeight.setText("Weight: 150");
        tvHeight.setText("Height: 50");
        tvHeadCircumference.setText("Head Circumference: 30");

        // Attach a listener to read the data at your data node
        databaseReference.child("0c2de94b-3fd4-4ab5-b118-78c074962893").child("babyInfoId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and whenever data at this location is updated
                String babyInfoId = dataSnapshot.getValue(String.class);

                if (babyInfoId != null) {
                    databaseReference.child(babyInfoId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Iterate through the children to get individual data fields
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                // Assuming the data structure is flat (not nested), you can directly get values
                                String key = childSnapshot.getKey();
                                String value = childSnapshot.getValue(String.class);

                                // Log key and value for debugging
                                Log.d("BabyVisualisation", "Key: " + key + ", Value: " + value);

                                // Update UI based on the key and value
                                if (key != null && value != null) {
                                    if (key.equals("fetalWeight")) {
                                        tvWeight.setText("Weight: " + value);
                                    } else if (key.equals("fetalLength")) {
                                        tvHeight.setText("Height: " + value);
                                    } else if (key.equals("headCircumference")) {
                                        tvHeadCircumference.setText("Head Circumference: " + value);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Failed to read value
                            // Handle the error, log, or show a message
                            Log.e("BabyVisualisation", "Error reading data: " + databaseError.getMessage());
                        }
                    });
                } else {
                    Log.e("BabyVisualisation", "babyInfoId is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                // Handle the error, log, or show a message
                Log.e("BabyVisualisation", "Error reading babyInfoId: " + databaseError.getMessage());
            }
        });
    }
}

