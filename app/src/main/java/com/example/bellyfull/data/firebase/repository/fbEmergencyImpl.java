package com.example.bellyfull.data.firebase.repository;

import android.content.Context;

import com.example.bellyfull.Constant.db_collection_constant;
import com.example.bellyfull.data.firebase.collection.User;
import com.example.bellyfull.data.firebase.firebase;
import com.example.bellyfull.data.firebase.ports.dbEmergency;
import com.example.bellyfull.data.firebase.ports.dbProfileCallback;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class fbEmergencyImpl implements dbEmergency {
    Context context;
    FirebaseFirestore db = firebase.getDatabase();

    public fbEmergencyImpl(Context context) {
        this.context = context;
    }
    @Override
    public void getUserHospital(String userId, dbProfileCallback callback) {
        db.collection(db_collection_constant.UserCollection)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        User user = documentSnapshot.toObject(User.class);
                        callback.onSuccess(user);
                    } else {
                        callback.onSuccess(null);
                    }
                });
    }
}
