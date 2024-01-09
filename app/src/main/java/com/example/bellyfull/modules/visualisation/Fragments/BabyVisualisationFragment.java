package com.example.bellyfull.modules.visualisation.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bellyfull.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BabyVisualisationFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Baby> babyArrayList;
    private MyAdapter myAdapter;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        db = FirebaseFirestore.getInstance();
        babyArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(requireContext(), babyArrayList);

        recyclerView.setAdapter(myAdapter);
        eventChangeListener();

        return view;
    }

    private void eventChangeListener() {
        db.collection("BABY_INFO")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            babyArrayList.add(document.toObject(Baby.class));
                        }
                        myAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("Firestore error", "Error getting documents: ", task.getException());
                    }

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                });
    }
}
