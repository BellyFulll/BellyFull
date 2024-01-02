package com.example.bellyfull.data.firebase.repository;

import android.content.Context;

import com.example.bellyfull.Constant.db_collection_constant;
import com.example.bellyfull.data.firebase.collection.User;
import com.example.bellyfull.data.firebase.firebase;
import com.example.bellyfull.data.firebase.ports.dbProfileCallback;
import com.example.bellyfull.data.firebase.ports.dbProfileRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.ExecutionException;

public class fbProfileRepositoryImpl implements dbProfileRepository {
    Context context;

    public fbProfileRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getUserDetails(String userId, dbProfileCallback callback) {
        FirebaseFirestore db = firebase.getDatabase();

        db.collection(db_collection_constant.UserCollection)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        System.out.println("success");
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                        User user = documentSnapshot.toObject(User.class);
                        System.out.println(user.getName());
                        callback.onSuccess(user);
                    } else {
                        callback.onSuccess(null);
                    }
                });
    }
}
