package com.example.bellyfull.modules.General;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.MainActivity;
import com.example.bellyfull.PreLoginActivity;
import com.example.bellyfull.R;
import com.example.bellyfull.modules.HomeFragmentDirections;
import com.example.bellyfull.utils.AlertDialogUtil;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button BtnBackProfile = view.findViewById(R.id.BtnBackProfile);
        Button BtnLogoutProfile = view.findViewById(R.id.BtnLogoutProfile);
        SharedPreferences preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = preferences.edit();

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
    }
}
