package com.example.bellyfull.modules.AdviceAndTips.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bellyfull.R;
import com.example.bellyfull.modules.AdviceAndTips.Adapter.AdviceAdapter;
import com.example.bellyfull.modules.AdviceAndTips.Models.AdviceItem;

import java.util.ArrayList;
import java.util.List;

public class AdviceTipsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advice_tips, container, false);

        // Accessing TextViews
        TextView nutritionalTextView = view.findViewById(R.id.nutritional);
        TextView exerciseReTextView = view.findViewById(R.id.exercise_re);
        TextView faqTextView = view.findViewById(R.id.faq);

        // Accessing includes
        View ngBloc = view.findViewById(R.id.ng_bloc);
        View erBloc = view.findViewById(R.id.er_bloc);
        View faqBloc = view.findViewById(R.id.faq_bloc);

        // Accessing additional TextViews
        TextView tvNutritionalGuidelines = view.findViewById(R.id.TVnutritionalGuidelines);
        TextView tvExerciseRecommendations = view.findViewById(R.id.TVexerciseRecommendations);
        TextView tvFaq = view.findViewById(R.id.TVfaq);

        // Setting text or performing other interactions as needed

        // RecyclerView for displaying advice items
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.GONE); // Set initial visibility to GONE

        // Fetch data from Firebase
        fetchDataFromFirebase(recyclerView);

        return view;
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<AdviceItem> adviceList) {
        // Creating and setting up the adapter
        AdviceAdapter adviceAdapter = new AdviceAdapter(adviceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adviceAdapter);
    }

    private void fetchDataFromFirebase(final RecyclerView recyclerView) {
        // Simulate fetching data from Firebase
        // Once the data is fetched, update the RecyclerView visibility
        // For example, after successfully fetching data:
        List<AdviceItem> adviceList = getDummyAdviceList();
        recyclerView.setVisibility(View.VISIBLE);
        setupRecyclerView(recyclerView, adviceList);
    }

    private List<AdviceItem> getDummyAdviceList() {
        // Populate this list with your advice items (title and content)
        List<AdviceItem> dummyList = new ArrayList<>();
        dummyList.add(new AdviceItem("Title 1", "Content 1"));
        dummyList.add(new AdviceItem("Title 2", "Content 2"));
        // Add more items as needed
        return dummyList;
    }

}
