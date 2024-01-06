package com.example.bellyfull.modules.EmergencyAndHelp.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.collection.User;
import com.example.bellyfull.data.firebase.ports.dbProfileCallback;
import com.example.bellyfull.data.firebase.repository.fbProfileRepositoryImpl;

public class MedicalInfoActivity extends AppCompatActivity {
    SharedPreferences preferences;
    dbProfileCallback callback = new dbProfileCallback() {
        @Override
        public void onSuccess(User user) { updateUI(user); }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_info);
        preferences = getSharedPreferences(preference_constant.pUserInfo, MODE_PRIVATE);
        String userId = preferences.getString(preference_constant.pUserId, "");
        fbProfileRepositoryImpl impl = new fbProfileRepositoryImpl(this);
        impl.getUserDetails(userId, callback);
    }
    private void updateUI(User user) {
        String name = user.getName();
        String contact = user.getContact();
        String address = user.getAddress();

        TextView nameTextView = findViewById(R.id.mumName);
        TextView contactTextView = findViewById(R.id.mumContactNo);
        TextView addressTextView = findViewById(R.id.mumAddress);

        nameTextView.append(name);
        contactTextView.append(contact);
        addressTextView.append("\n" + address);
    }
}
