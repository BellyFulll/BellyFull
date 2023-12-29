package com.example.bellyfull.data.firebase.ports;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public interface dbLoginCallback {
    void onCallback(QueryDocumentSnapshot document);
}
