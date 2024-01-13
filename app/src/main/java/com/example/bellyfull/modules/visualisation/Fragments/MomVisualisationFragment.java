package com.example.bellyfull.modules.visualisation.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bellyfull.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//Trial
public class MomVisualisationFragment extends Fragment {

    private DatabaseReference databaseReference;
    private TextView tvCardViewBig;

    public MomVisualisationFragment() {
        super(R.layout.fragment_mom_visualisation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference = database.getReference("users").child(userId).child("6eee6c1d-d13f-4297-b968-cfaa35623099"); // Replace with your actual mom info id
        }

        // Initialize TextView
        tvCardViewBig = view.findViewById(R.id.sleepanalysis_text);

        // Attach a listener to read the data at your data node
        if (databaseReference != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and whenever data at this location is updated

                    if (dataSnapshot.exists()) {
                        String foodDiary = dataSnapshot.child("foodDiary").getValue(String.class);

                        if (foodDiary != null) {
                            tvCardViewBig.setText("Food Diary Entry: " + foodDiary);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("MomVisualisationFragment", "Error reading data from Firebase: " + databaseError.getMessage());
                    Toast.makeText(getContext(), "Failed to read data. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
