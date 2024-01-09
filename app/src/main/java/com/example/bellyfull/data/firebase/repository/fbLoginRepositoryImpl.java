package com.example.bellyfull.data.firebase.repository;

import androidx.annotation.NonNull;

import com.example.bellyfull.Constant.db_collection_constant;
import com.example.bellyfull.data.firebase.firebase;
import com.example.bellyfull.data.firebase.ports.dbLoginCallback;
import com.example.bellyfull.data.firebase.ports.fbLoginRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class fbLoginRepositoryImpl implements fbLoginRepository {

    @Override
    public void getPassword(String email, dbLoginCallback callback) {
        FirebaseFirestore db = firebase.getDatabase();
        StringBuilder sb = new StringBuilder();
        db.collection(db_collection_constant.UserCollection)
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document != null) {
                                    callback.onCallback(document);
                                }
                            }
                        }
                    }
                });
    }
}
