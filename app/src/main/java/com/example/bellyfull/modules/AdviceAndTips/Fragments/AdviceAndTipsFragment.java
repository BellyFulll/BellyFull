package com.example.bellyfull.modules.AdviceAndTips.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bellyfull.R;
import com.example.bellyfull.modules.AdviceAndTips.Adapter.AdviceAdapter;
import com.example.bellyfull.modules.AdviceAndTips.Models.AdviceItem;

import java.util.ArrayList;
import java.util.List;

// Inside AdviceAndTipsFragment.java

public class AdviceAndTipsFragment extends Fragment {
    private AdviceAdapter ngAdapter, erAdapter, faqAdapter;
    private List<AdviceItem> ngList, erList, faqList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advice_and_tips, container, false);

        // Initialize RecyclerViews and adapters
        ngList = new ArrayList<>();
        erList = new ArrayList<>();
        faqList = new ArrayList<>();

        ngAdapter = new AdviceAdapter(ngList);
        erAdapter = new AdviceAdapter(erList);
        faqAdapter = new AdviceAdapter(faqList);

        setupRecyclerView(view.findViewById(R.id.rvng), ngAdapter);
        setupRecyclerView(view.findViewById(R.id.rver), erAdapter);
        setupRecyclerView(view.findViewById(R.id.rvfaq), faqAdapter);

        // Populate ngList (Nutritional Guidelines)
        ngList.add(new AdviceItem("Balanced Diet During Pregnancy",
                "Ensure a diverse and balanced diet rich in fruits, vegetables, whole grains, lean proteins, and dairy. Proper nutrition supports your baby's growth and development."));

        ngList.add(new AdviceItem("Key Nutrients for Moms-to-Be",
                "Focus on essential nutrients like folic acid, iron, calcium, and omega-3 fatty acids. These play crucial roles in preventing birth defects, supporting blood health, and aiding in fetal brain development."));

        ngList.add(new AdviceItem("Hydration Matters",
                "Stay well-hydrated by drinking plenty of water throughout the day. Proper hydration supports amniotic fluid, helps prevent constipation, and assists in nutrient transportation."));

        // Populate erList (Exercise Recommendations)
        erList.add(new AdviceItem("Safe Pregnancy Exercises",
                "Engage in low-impact exercises such as walking, swimming, and prenatal yoga. These activities enhance cardiovascular health, flexibility, and can ease discomfort during pregnancy."));

        erList.add(new AdviceItem("Strength Training with Caution",
                "Incorporate light resistance training to maintain muscle tone. However, avoid heavy lifting and exercises that put excessive strain on your abdominal muscles."));

        erList.add(new AdviceItem("Pelvic Floor Exercises",
                "Include pelvic floor exercises (Kegels) to strengthen the muscles supporting the uterus, bladder, and bowels. These exercises can aid in preventing urinary incontinence."));

        // Populate faqList (Frequently Asked Questions)
        faqList.add(new AdviceItem("Managing Morning Sickness",
                "Morning sickness can occur at any time of the day. To manage it, eat small, frequent meals, stay hydrated, and consider ginger supplements. Consult your healthcare provider for severe cases."));

        faqList.add(new AdviceItem("Traveling While Pregnant",
                "Consult your healthcare provider before making travel plans. Generally, it's safe to travel during the second trimester. Stay hydrated, move around periodically, and wear compression stockings to reduce the risk of blood clots."));

        faqList.add(new AdviceItem("Choosing a Prenatal Vitamin",
                "Select a prenatal vitamin with folic acid, iron, calcium, and DHA. Your healthcare provider can recommend a suitable supplement based on your individual needs."));

        // Notify adapters about the data changes
        ngAdapter.notifyDataSetChanged();
        erAdapter.notifyDataSetChanged();
        faqAdapter.notifyDataSetChanged();

        return view;
    }

    private void setupRecyclerView(RecyclerView recyclerView, AdviceAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }
}

/*
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