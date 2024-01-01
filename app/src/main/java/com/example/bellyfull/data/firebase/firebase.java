package com.example.bellyfull.data.firebase;

import com.google.firebase.firestore.FirebaseFirestore;

public class firebase {
    private static volatile FirebaseFirestore INSTANCE;

    private firebase() {
    }

    public static FirebaseFirestore getDatabase() {
        if (INSTANCE == null) {
            INSTANCE = FirebaseFirestore.getInstance();
        }
        return INSTANCE;
    }
}
