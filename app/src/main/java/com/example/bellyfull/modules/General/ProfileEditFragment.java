package com.example.bellyfull.modules.General;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.repository.fbProfileRepositoryImpl;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProfileEditFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private Date selectedDate = null;
    TextView TVDateOfConception;


    public ProfileEditFragment() {
        super(R.layout.fragment_edit_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fbProfileRepositoryImpl impl = new fbProfileRepositoryImpl(getContext());
        Button BtnBackProfileEdit = view.findViewById(R.id.BtnBackProfileEdit);
        Button BtnSaveEdit = view.findViewById(R.id.BtnSaveEdit);
        EditText ETNameUpdate = view.findViewById(R.id.ETNameUpdate);
        EditText ETEmailUpdate = view.findViewById(R.id.ETEmailUpdate);
        EditText ETContactUpdate = view.findViewById(R.id.ETContactUpdate);
        EditText ETAddressUpdate = view.findViewById(R.id.ETAddressUpdate);
        EditText ETHospitalContactUpdate = view.findViewById(R.id.ETHospitalContactUpdate);
        EditText ETEmergencyResponderEmail = view.findViewById(R.id.ETEmergencyResponderEmail);
        LinearLayout dateOfConceptionClickable = view.findViewById(R.id.dateOfConceptionClickable);

        TVDateOfConception = view.findViewById(R.id.TVDateOfConception);

        SharedPreferences preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
        String userId = preferences.getString(preference_constant.pUserId, "");

        BtnBackProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ProfileEditFragmentDirections.actionProfileEditFragmentToProfileFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        dateOfConceptionClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfConceptionClick();
            }
        });

        BtnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = ETNameUpdate.getText().toString();
                String updatedEmail = ETEmailUpdate.getText().toString();
                String updatedContact = ETContactUpdate.getText().toString();
                String updatedAddress = ETAddressUpdate.getText().toString();
                String updatedHospitalContact = ETHospitalContactUpdate.getText().toString();
                String updatedEmergencyResponderEmail = ETEmergencyResponderEmail.getText().toString();

                if (!updatedName.matches("")) {
                    impl.updateUserName(userId, updatedName);
                    ETNameUpdate.getText().clear();
                }
                if (!updatedEmail.matches("")) {
                    impl.updateUserEmail(userId, updatedEmail);
                    ETEmailUpdate.getText().clear();
                }
                if (!updatedContact.matches("")) {
                    impl.updateUserContact(userId, updatedContact);
                    ETContactUpdate.getText().clear();
                }
                if (!updatedAddress.matches("")) {
                    impl.updateUserAddress(userId, updatedAddress);
                    ETAddressUpdate.getText().clear();
                }
                if (!updatedHospitalContact.matches("")) {
                    impl.updateUserPreferredHospitalContact(userId, updatedHospitalContact);
                    ETHospitalContactUpdate.getText().clear();
                }
                if(!updatedEmergencyResponderEmail.matches("")){
                    impl.updateEmergencyResponderEmail(userId, updatedEmergencyResponderEmail);
                    ETEmergencyResponderEmail.getText().clear();
                }
                if(selectedDate != null){
                    impl.updateDateOfConception(userId, selectedDate.getTime());
                    TVDateOfConception = view.findViewById(R.id.TVDateOfConception);
                    TVDateOfConception.setText("");
                }

                Toast.makeText(getContext(), "Credentials updated successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onDateOfConceptionClick() {

        Calendar currentDate = Calendar.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Event Date");
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TVDateOfConception.setText(currentDateString);
        selectedDate = c.getTime();
    }
}
