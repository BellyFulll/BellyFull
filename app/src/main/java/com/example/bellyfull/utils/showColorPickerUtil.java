package com.example.bellyfull.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.bellyfull.R;
import com.example.bellyfull.modules.PersonalisedNotifications.EventCategory;

public class showColorPickerUtil {
    private static Context context;
    private String selectedPrimaryHex;
    private String selectedSecondaryHex;
    private String selectedIconHex;
    private TextView previousSelectedTextView = null;
    private int previousSelectedPrimaryColor = 0;

    public showColorPickerUtil(Context context) {
        this.context = context;
    }

    public interface ColorSelectedCallback {
        void onCategoryAdded(EventCategory eventCategory);
    }

    public AlertDialog.Builder build(ColorSelectedCallback callback) {
        final int[] primaryColors = context.getResources().getIntArray(R.array.event_category_primary_colors);
        final int[] selectedColors = context.getResources().getIntArray(R.array.event_category_selected_colors);
        final int[] iconColors = context.getResources().getIntArray(R.array.event_category_icon_colors);
        String[] colorNames = context.getResources().getStringArray(R.array.event_category_color_names);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_event_category, null);
        GridLayout gridLayout = dialogView.findViewById(R.id.gridLayout);
        final EditText input = dialogView.findViewById(R.id.editText);
        gridLayout.setColumnCount(7);
        gridLayout.setRowCount((int) Math.ceil((double) primaryColors.length / 4));

        for (int i = 0; i < primaryColors.length; i++) {
            TextView colorTextView = createColorTextView(colorNames[i], primaryColors[i], selectedColors[i], iconColors[i]);
            // Add the TextView to the GridLayout
            gridLayout.addView(colorTextView);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String categoryText = input.getText().toString();

                if (categoryText.equals("") || previousSelectedTextView == null || previousSelectedPrimaryColor == 0) {
                    Toast.makeText(context, "please enter in a title and select a color for your new category", Toast.LENGTH_SHORT).show();
                    return;
                }

                EventCategory eventCategory = new EventCategory(selectedPrimaryHex, selectedSecondaryHex, selectedIconHex, categoryText);
                callback.onCategoryAdded(eventCategory);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder;
    }

    private TextView createColorTextView(String colorName, int primaryColor, int selectedColor, int iconColor) {
        TextView textView = new TextView(context);
        textView.setText(colorName);
        textView.setBackgroundColor(primaryColor);
        textView.setPadding(16, 16, 16, 16);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previousSelectedPrimaryColor != 0 && previousSelectedTextView != null) {
                    previousSelectedTextView.setBackgroundColor(previousSelectedPrimaryColor);
                }

                textView.setBackgroundColor(selectedColor);
                selectedPrimaryHex = String.format("#%06X", (0xFFFFFF & primaryColor));
                selectedSecondaryHex = String.format("#%06X", (0xFFFFFF & selectedColor));
                selectedIconHex = String.format("#%06X", (0xFFFFFF & iconColor));
                previousSelectedTextView = textView;
                previousSelectedPrimaryColor = primaryColor;
            }
        });

        return textView;
    }
}