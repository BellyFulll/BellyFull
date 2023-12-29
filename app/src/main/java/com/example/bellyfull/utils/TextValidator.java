package com.example.bellyfull.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidator {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "^.{9,}$";
    private static final String NAME_PATTERN =
            "^.{3,}$";

    public static boolean validateEmail(TextView textView, String email) {
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = emailPattern.matcher(email);
        if (!matcher.matches()) {
            textView.setError("Please enter a valid email");
            return false;
        }
        return true;
    }

    public static boolean validatePassword(TextView textView, String password) {
        Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = passwordPattern.matcher(password);
        if (!matcher.matches()) {
            textView.setError("Please enter a password longer than 8 characters");
            return false;
        }
        return true;
    }

    public static boolean validateName(TextView textView, String name) {
        Pattern namePattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = namePattern.matcher(name);
        if (!matcher.matches()) {
            textView.setError("Please enter a name more than 3 characters");
            return false;
        }
        return true;
    }


}
