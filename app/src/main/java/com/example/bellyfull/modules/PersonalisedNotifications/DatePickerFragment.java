package com.example.bellyfull.modules.PersonalisedNotifications;

//import android.support.v4.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Check if the hosting fragment implements OnDateSetListener
        if (getTargetFragment() instanceof DatePickerDialog.OnDateSetListener) {
            return new DatePickerDialog(requireContext(), (DatePickerDialog.OnDateSetListener) getTargetFragment(), year, month, day);
        } else {
            // Log an error or handle the case where the fragment does not implement OnDateSetListener
            return super.onCreateDialog(savedInstanceState);
        }

//        return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),year, month,day);
    }
}
