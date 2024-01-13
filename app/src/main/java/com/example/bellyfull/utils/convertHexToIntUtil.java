package com.example.bellyfull.utils;

import android.graphics.Color;

public class convertHexToIntUtil {
    public static int change(String hexColor) {
        try {
            int color = Color.parseColor(hexColor);
            return color;
        } catch (IllegalArgumentException e) {
            // Handle the case where the hexColor is not a valid color
            return 1;
        }
    }
}
