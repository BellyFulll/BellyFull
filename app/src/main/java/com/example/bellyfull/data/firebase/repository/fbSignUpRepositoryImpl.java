package com.example.bellyfull.data.firebase.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bellyfull.Constant.db_constant;
import com.example.bellyfull.data.firebase.collection.User;
import com.example.bellyfull.data.firebase.firebase;
import com.example.bellyfull.data.firebase.ports.fbSignUpRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class fbSignUpRepositoryImpl implements fbSignUpRepository {
    Context context;

    public fbSignUpRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void registerUser(String name, String email, String password) {
        String userId = UUID.randomUUID().toString();
        User user = new User(userId, name, email, password, null, null);
        FirebaseFirestore db = firebase.getDatabase();

        db.collection(db_constant.UserCollection).document(userId.toString())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Successfully registered", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to register user", Toast.LENGTH_SHORT).show();
                        Log.d("register.failure", e.toString());
                    }
                });
    }
}
