package com.example.bellyfull.data.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * Cloud Firestore is a NoSQL, document-oriented database. Unlike a SQL database, there are no tables or rows.
 * Instead, you store data in documents, which are organized into collections.
 * Each document contains a set of key-value pairs. Cloud Firestore is optimized for storing large collections of small documents.
 */

public class sampleDBCode {
    // Create a firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Create a TAG for logging
    private static final String TAG = "DocSnippets";

    // Method to add a user to the database
    public void addAdaLovelace() {
        // [START add_ada_lovelace]
        // Create a new user with a first and last name
        // This is data from our own side
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        // Add a new document with a generated ID
        db.collection("users") // The collection name is users. Collections are like tables in SQL.
                .add(user) // This row adds the one user record to the database as a document.
                /** The document will look something like this, and this one document will comprise of this one user's record.
                 * {
                 *    "first": "Ada",
                 *    "last": "Lovelace",
                 *    "born": 1815
                 * }
                 *  The record of other users will be added as other documents in the same collection.
                 */

                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() { // This is a listener that listens for success
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() { // This is a listener that listens for failure
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        // [END add_ada_lovelace]
    }

    public void getAllUsers() {
        // [START get_all_users]
        db.collection("users")
                .get() // This gets all the documents in the collection
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() { // This is a listener that listens for completion
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            /**
                             * The way to access the data is by iterating through the documents.
                             * The documents are stored in the task object.
                             * The task object is a list of documents.
                             * Each document is a QueryDocumentSnapshot object.
                             * The QueryDocumentSnapshot object has a method called getData() which returns a Map<String, Object>.
                             * The Map<String, Object> is the data of the document.
                             */
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        // [END get_all_users]
    }
}
