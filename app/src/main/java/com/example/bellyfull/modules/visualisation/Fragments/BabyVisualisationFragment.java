package com.example.bellyfull.modules.visualisation.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bellyfull.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
public class BabyVisualisationFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Baby> babyArrayList;
    private MyAdapter myAdapter;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private Spinner spinner; //Declare at outside

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baby_visualisation, container, false);

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // Use LinearLayoutManager with reverseLayout set to true
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true); // Optional, ensures that the last item stays at the bottom
        recyclerView.setLayoutManager(layoutManager);

        db = FirebaseFirestore.getInstance();
        babyArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(requireContext(), babyArrayList);

        recyclerView.setAdapter(myAdapter);
        eventChangeListener();

        spinner = view.findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.spinner_items, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        FloatingActionButton btnBabyInput = view.findViewById(R.id.btnBabyInput);
        btnBabyInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle FAB click
                navigateToBabyInputFragment();
            }
        });

        return view;
    }

    private void eventChangeListener() {
        db.collection("BABY_INFO")
                .orderBy("timestamp", Query.Direction.DESCENDING) // Assuming you have a timestamp field for sorting
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e("Firestore error", "Error getting documents: ", error);
                        return;
                    }

                    if (value != null) {
                        babyArrayList.clear(); // Clear existing data
                        for (DocumentSnapshot document : value) {
                            babyArrayList.add(0, document.toObject(Baby.class)); // Add new items at the beginning
                        }
                        myAdapter.notifyDataSetChanged();
                    }

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void navigateToBabyInputFragment() {
        // Use Navigation Component to navigate to BabyInputFragment
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_babyVisualisationFragment_to_babyInputFragment);
    }
}
