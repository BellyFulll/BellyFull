package com.example.bellyfull.data.firebase.repository;

import android.content.Context;
import android.widget.Toast;

import com.example.bellyfull.Constant.db_collection_constant;
import com.example.bellyfull.data.firebase.collection.User;
import com.example.bellyfull.data.firebase.firebase;
import com.example.bellyfull.data.firebase.ports.fbSignUpRepository;
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
        User user = new User(userId, name, email, password, null, null, null);
        FirebaseFirestore db = firebase.getDatabase();

        db.collection(db_collection_constant.UserCollection).document(userId.toString())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Successfully registered", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
