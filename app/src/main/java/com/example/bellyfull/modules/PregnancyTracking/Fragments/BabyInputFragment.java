package com.example.bellyfull.modules.PregnancyTracking.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.repository.fbBabyInfoRepositoryImpl;

import java.util.UUID;


public class BabyInputFragment extends Fragment {
    EditText ETFetalLength;
    EditText ETFetalWeight;
    EditText ETHeadCircumference;
    EditText ETGrowthNotes;
    fbBabyInfoRepositoryImpl impl;

    public BabyInputFragment() {
        super(R.layout.fragment_baby_input);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        impl = new fbBabyInfoRepositoryImpl(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        ImageView IVFetalLengthInc = view.findViewById(R.id.IVFetalLengthInc);
        ImageView IVFetalLengthDec = view.findViewById(R.id.IVFetalLengthDec);
        ImageView IVFetalWeightInc = view.findViewById(R.id.IVFetalWeightInc);
        ImageView IVFetalWeightDec = view.findViewById(R.id.IVFetalWeightDec);
        ImageView IVHeadCircumferenceInc = view.findViewById(R.id.IVHeadCircumferenceInc);
        ImageView IVHeadCircumferenceDec = view.findViewById(R.id.IVHeadCircumferenceDec);
        ETFetalLength = getView().findViewById(R.id.ETFetalLength);
        ETFetalWeight = getView().findViewById(R.id.ETFetalWeight);
        ETHeadCircumference = getView().findViewById(R.id.ETHeadCircumference);
        ETGrowthNotes = getView().findViewById(R.id.ETGrowthNotes);

        IVFetalLengthInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double currentValue = Double.parseDouble(ETFetalLength.getText().toString());
                currentValue += 0.5;
                ETFetalLength.setText(currentValue.toString());
            }
        });

        IVFetalLengthDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double currentValue = Double.parseDouble(ETFetalLength.getText().toString());
                if (currentValue <= 0) return;
                currentValue -= 0.5;
                ETFetalLength.setText(currentValue.toString());
            }
        });

        IVFetalWeightInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double currentValue = Double.parseDouble(ETFetalWeight.getText().toString());
                currentValue += 0.5;
                ETFetalWeight.setText(currentValue.toString());
            }
        });

        IVFetalWeightDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double currentValue = Double.parseDouble(ETFetalWeight.getText().toString());
                if (currentValue <= 0) return;
                currentValue -= 0.5;
                ETFetalWeight.setText(currentValue.toString());
            }
        });

        IVHeadCircumferenceInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double currentValue = Double.parseDouble(ETHeadCircumference.getText().toString());
                currentValue += 0.5;
                ETHeadCircumference.setText(currentValue.toString());
            }
        });

        IVHeadCircumferenceDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double currentValue = Double.parseDouble(ETHeadCircumference.getText().toString());
                if (currentValue <= 0) return;
                currentValue -= 0.5;
                ETHeadCircumference.setText(currentValue.toString());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = BabyInputFragmentDirections.actionBabyInputFragmentToHomeFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
                String userId = preferences.getString(preference_constant.pUserId, "");
                String babyInfoId = UUID.randomUUID().toString();
                impl.createBabyInfo(userId, babyInfoId);

                String updatedFetalLength = ETFetalLength.getText().toString();
                String updatedFetalWeight = ETFetalWeight.getText().toString();
                String updatedHeadCircumference = ETHeadCircumference.getText().toString();
                String updatedGrowthNotes = ETGrowthNotes.getText().toString();

                if (!updatedFetalLength.matches("")) {
                    impl.setBabyInfoFetalLength(babyInfoId, Double.parseDouble(updatedFetalLength));
                    ETFetalLength.getText().clear();
                }
                if (!updatedFetalWeight.matches("")) {
                    impl.setBabyInfoFetalWeight(babyInfoId, Double.parseDouble(updatedFetalWeight));
                    ETFetalWeight.getText().clear();
                }
                if (!updatedHeadCircumference.matches("")) {
                    impl.setBabyInfoHeadCircumference(babyInfoId, Double.parseDouble(updatedHeadCircumference));
                    ETHeadCircumference.getText().clear();
                }
                if (!updatedGrowthNotes.matches("")) {
                    impl.setBabyInfoGrowthNotes(babyInfoId, updatedGrowthNotes);
                    ETGrowthNotes.getText().clear();
                }

                ETGrowthNotes.clearFocus();
                Toast.makeText(getContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}