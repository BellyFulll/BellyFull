package com.example.bellyfull.modules.General;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.repository.fbProfileRepositoryImpl;

public class ProfileEditFragment extends Fragment {

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

        SharedPreferences preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
        String userId = preferences.getString(preference_constant.pUserId, "");

        BtnBackProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ProfileEditFragmentDirections.actionProfileEditFragmentToProfileFragment();
                Navigation.findNavController(view).navigate(action);
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

                Toast.makeText(getContext(), "Credentials updated successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
