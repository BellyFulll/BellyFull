package com.example.bellyfull.modules.General;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.PreLoginActivity;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.collection.User;
import com.example.bellyfull.data.firebase.ports.dbProfileCallback;
import com.example.bellyfull.data.firebase.repository.fbProfileRepositoryImpl;
import com.example.bellyfull.utils.AlertDialogUtil;

public class ProfileFragment extends Fragment {
    SharedPreferences preferences;
    SharedPreferences.Editor spEditor;

    dbProfileCallback callback = new dbProfileCallback() {
        @Override
        public void onSuccess(User user) {
            updateUI(user);
        }
    };

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
        String userId = preferences.getString(preference_constant.pUserId, "");
        spEditor = preferences.edit();
        fbProfileRepositoryImpl impl = new fbProfileRepositoryImpl(getContext());
        impl.getUserDetails(userId, callback);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button BtnBackProfile = view.findViewById(R.id.BtnBackProfile);
        Button BtnLogoutProfile = view.findViewById(R.id.BtnLogoutProfile);
        Button BtnEditProfile = view.findViewById(R.id.BtnEditProfile);

        BtnBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ProfileFragmentDirections.actionProfileFragmentToHomeFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        BtnLogoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogUtil alertDialogUtil = new AlertDialogUtil(getContext(), "Are you sure?");
                alertDialogUtil.showAlertDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        spEditor.putBoolean(preference_constant.pIsLogin, false);
                        spEditor.remove(preference_constant.pUserId);
                        spEditor.commit();

                        Intent intent = new Intent(getActivity(), PreLoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        });

        BtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }


    private void updateUI(User user) {
        TextView TVNameValueProfile = getView().findViewById(R.id.TVNameValueProfile);
        TextView TVEmailValueProfile = getView().findViewById(R.id.TVEmailValueProfile);
        TextView TVContactValueProfile = getView().findViewById(R.id.TVContactValueProfile);
        TextView TVAddressValueProfile = getView().findViewById(R.id.TVAddressValueProfile);
        TextView TVPreferredHospitalContactValueProfile = getView().findViewById(R.id.TVPreferredHospitalContactValueProfile);

        TVNameValueProfile.setText(user.getName());
        TVEmailValueProfile.setText(user.getEmail());
        TVContactValueProfile.setText(user.getContact());
        TVAddressValueProfile.setText(user.getAddress());
        TVPreferredHospitalContactValueProfile.setText(user.getPreferredHospitalContact());
    }
}
