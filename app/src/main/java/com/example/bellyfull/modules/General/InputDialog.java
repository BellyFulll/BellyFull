package com.example.bellyfull.modules.General;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.bellyfull.R;


public class InputDialog extends AppCompatDialogFragment {
    private EditText editText;

    public InputDialog(EditText editText) {
        this.editText = editText;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        EditText ETDialogInput = view.findViewById(R.id.ETDialogInput);
        String editTextInput = editText.getText().toString();
        ETDialogInput.setText(editTextInput);
        Button BtnDialogInputCancel = view.findViewById(R.id.BtnDialogInputCancel);
        Button BtnDialogInputUpdate = view.findViewById(R.id.BtnDialogInputUpdate);

        BtnDialogInputCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        BtnDialogInputUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String condition = ETDialogInput.getText().toString();
                editText.setText(condition);
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }

}