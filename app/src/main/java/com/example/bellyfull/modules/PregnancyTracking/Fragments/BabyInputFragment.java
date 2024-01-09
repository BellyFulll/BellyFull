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
import com.example.bellyfull.utils.hideKeyboardUtils;

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
                String fetalLength = ETFetalLength.getText().toString();
                System.out.println(fetalLength);
                Double currentValue = 0.0;
                if (!fetalLength.matches("")) {
                    currentValue = Double.parseDouble(fetalLength);
                    currentValue += 0.5;
                } else {
                    currentValue = 0.5;
                }
                ETFetalLength.setText(currentValue.toString());
                ETFetalLength.clearFocus();
                hideKeyboardUtils.hideKeyboard(getActivity(), v);
            }
        });

        IVFetalLengthDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fetalLength = ETFetalLength.getText().toString();
                Double currentValue = 0.0;
                if (!fetalLength.matches("") && !fetalLength.matches("0.0")) {
                    currentValue = Double.parseDouble(fetalLength);
                    currentValue -= 0.5;
                } else {
                    return;
                }
                ETFetalLength.setText(currentValue.toString());
                ETFetalLength.clearFocus();
                hideKeyboardUtils.hideKeyboard(getActivity(), v);
            }
        });

        IVFetalWeightInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fetalWeight = ETFetalWeight.getText().toString();
                Double currentValue = 0.0;
                if (!fetalWeight.matches("")) {
                    currentValue = Double.parseDouble(fetalWeight);
                    currentValue += 0.5;
                } else {
                    currentValue = 0.5;
                }
                ETFetalWeight.setText(currentValue.toString());
                ETFetalWeight.clearFocus();
                hideKeyboardUtils.hideKeyboard(getActivity(), v);
            }
        });

        IVFetalWeightDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fetalWeight = ETFetalWeight.getText().toString();
                Double currentValue = 0.0;
                if (!fetalWeight.matches("") && !fetalWeight.matches("0.0")) {
                    currentValue = Double.parseDouble(fetalWeight);
                    currentValue -= 0.5;
                } else {
                    return;
                }
                ETFetalWeight.setText(currentValue.toString());
                ETFetalWeight.clearFocus();
                hideKeyboardUtils.hideKeyboard(getActivity(), v);
            }
        });

        IVHeadCircumferenceInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headCircumference = ETHeadCircumference.getText().toString();
                Double currentValue = 0.0;
                if (!headCircumference.matches("")) {
                    currentValue = Double.parseDouble(headCircumference);
                    currentValue += 0.5;
                } else {
                    currentValue = 0.5;
                }
                ETHeadCircumference.setText(currentValue.toString());
                ETHeadCircumference.clearFocus();
                hideKeyboardUtils.hideKeyboard(getActivity(), v);
            }
        });

        IVHeadCircumferenceDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headCircumference = ETHeadCircumference.getText().toString();
                Double currentValue = 0.0;
                if (!headCircumference.matches("") && !headCircumference.matches("0.0")) {
                    currentValue = Double.parseDouble(headCircumference);
                    currentValue -= 0.5;
                } else {
                    return;
                }
                ETHeadCircumference.setText(currentValue.toString());
                ETHeadCircumference.clearFocus();
                hideKeyboardUtils.hideKeyboard(getActivity(), v);
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

                // After updating the baby information, navigate to BabyVisualizationFragment
                NavDirections action = BabyInputFragmentDirections.actionBabyInputFragmentToBabyVisualisationFragment();
                Navigation.findNavController(view).navigate(action);

            }
        });
    }
}