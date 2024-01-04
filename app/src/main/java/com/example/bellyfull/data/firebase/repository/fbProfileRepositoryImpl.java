package com.example.bellyfull.data.firebase.repository;

import android.content.Context;

import com.example.bellyfull.Constant.db_collection_constant;
import com.example.bellyfull.data.firebase.collection.User;
import com.example.bellyfull.data.firebase.firebase;
import com.example.bellyfull.data.firebase.ports.dbProfileCallback;
import com.example.bellyfull.data.firebase.ports.dbProfileRepository;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class fbProfileRepositoryImpl implements dbProfileRepository {
    Context context;
    FirebaseFirestore db = firebase.getDatabase();

    public fbProfileRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getUserDetails(String userId, dbProfileCallback callback) {
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

    @Override
    public void updateUserName(String userId, String name) {
        db.collection(db_collection_constant.UserCollection)
                .document(userId)
                .update("name", name);
    }

    @Override
    public void updateUserEmail(String userId, String email) {
        db.collection(db_collection_constant.UserCollection)
                .document(userId)
                .update("email", email);
    }

    @Override
    public void updateUserContact(String userId, String contact) {
        db.collection(db_collection_constant.UserCollection)
                .document(userId)
                .update("contact", contact);
    }

    @Override
    public void updateUserAddress(String userId, String address) {
        db.collection(db_collection_constant.UserCollection)
                .document(userId)
                .update("address", address);
    }

    @Override
    public void updateUserPreferredHospitalContact(String userId, String preferredHospitalContact) {
        db.collection(db_collection_constant.UserCollection)
                .document(userId)
                .update("preferredHospitalContact", preferredHospitalContact);
    }
}
