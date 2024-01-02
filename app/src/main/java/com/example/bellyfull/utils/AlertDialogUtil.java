package com.example.bellyfull.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class AlertDialogUtil {
    private Context context;
    private String title;

    public AlertDialogUtil(Context context, String title) {
        this.context = context;
        this.title = title;
    }

    public void showAlertDialog(DialogInterface.OnClickListener positiveOnClick, DialogInterface.OnClickListener negativeOnClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);

        builder.setPositiveButton("Confirm", positiveOnClick);
        builder.setNegativeButton(android.R.string.cancel, negativeOnClick);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
