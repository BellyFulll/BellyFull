package com.example.bellyfull.modules.AdviceAndTips.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bellyfull.R;
import com.example.bellyfull.modules.AdviceAndTips.Adapter.AdviceAdapter;
import com.example.bellyfull.modules.AdviceAndTips.Models.AdviceItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AdviceAndTipsFragment extends Fragment {
    private AdviceAdapter ngAdapter, erAdapter, faqAdapter;
    private List<AdviceItem> ngList, erList, faqList;
    //private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advice_and_tips, container, false);

        // Initialize views
        //progressBar = view.findViewById(R.id.progressBar);

        // Initialize RecyclerViews and adapters
        ngList = new ArrayList<>();
        erList = new ArrayList<>();
        faqList = new ArrayList<>();

        ngAdapter = new AdviceAdapter(ngList);
        erAdapter = new AdviceAdapter(erList);
        faqAdapter = new AdviceAdapter(faqList);

        setupRecyclerView(view.findViewById(R.id.rvng), ngAdapter, "ngItems");
        setupRecyclerView(view.findViewById(R.id.rver), erAdapter, "erItems");
        setupRecyclerView(view.findViewById(R.id.rvfaq), faqAdapter, "faqItems");

        // Fetch data from Firebase
        //fetchDataFromFirebase();

        return view;
    }

    private void setupRecyclerView(RecyclerView recyclerView, AdviceAdapter adapter, String dataPath) {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        // Fetch data from Firebase for the specified path
        //fetchAdviceItems(adapter, dataPath);
    }

    /*
    private void fetchAdviceItems(AdviceAdapter adapter, String dataPath) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            String fullPath = "users/" + userId + "/" + dataPath;

            databaseReference.child(fullPath).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<AdviceItem> adviceItems = new ArrayList<>();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AdviceItem adviceItem = snapshot.getValue(AdviceItem.class);
                        if (adviceItem != null) {
                            adviceItems.add(adviceItem);
                        }
                    }

                    // Update the adapter with fetched data
                    adapter.setAdviceList(adviceItems);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors, e.g., show an error message
                }
            });
        }
    }

    private void fetchDataFromFirebase() {
        fetchAdviceItems(additionalAdapter, "additionalItems");
    }
}

     */

}