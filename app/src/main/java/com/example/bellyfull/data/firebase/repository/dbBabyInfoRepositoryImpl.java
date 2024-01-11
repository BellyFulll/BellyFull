package com.example.bellyfull.data.firebase.repository;

import android.content.Context;

import com.example.bellyfull.Constant.db_collection_constant;
import com.example.bellyfull.data.firebase.collection.BabyInfo;
import com.example.bellyfull.data.firebase.firebase;
import com.example.bellyfull.data.firebase.ports.dbBabyInfoCallback;
import com.example.bellyfull.data.firebase.ports.dbBabyInfoRepository;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class dbBabyInfoRepositoryImpl implements dbBabyInfoRepository {
    Context context;
    FirebaseFirestore db = firebase.getDatabase();

    public dbBabyInfoRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getBabyInfo(String userId, dbBabyInfoCallback callback) {
        db.collection(db_collection_constant.BabyInfoCollection)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        BabyInfo babyInfo = documentSnapshot.toObject(BabyInfo.class);
                        callback.onSuccess(babyInfo);
                    } else {
                        callback.onSuccess(null);
                    }
                });
    }

    @Override
    public void createBabyInfo(String userId, String babyInfoId) {
        BabyInfo babyInfo = new BabyInfo(babyInfoId, null, null, null, null, userId, new Date());
        db.collection(db_collection_constant.BabyInfoCollection).document(babyInfoId.toString())
                .set(babyInfo);
    }

    @Override
    public void setBabyInfoFetalLength(String babyInfoId, Double fetalLength) {
        db.collection(db_collection_constant.BabyInfoCollection).document(babyInfoId.toString())
                .update("fetalLength", fetalLength);
    }

    @Override
    public void setBabyInfoFetalWeight(String babyInfoId, Double fetalWeight) {
        db.collection(db_collection_constant.BabyInfoCollection).document(babyInfoId.toString())
                .update("fetalWeight", fetalWeight);
    }

    @Override
    public void setBabyInfoHeadCircumference(String babyInfoId, Double headCircumference) {
        db.collection(db_collection_constant.BabyInfoCollection).document(babyInfoId.toString())
                .update("headCircumference", headCircumference);
    }

    @Override
    public void setBabyInfoGrowthNotes(String babyInfoId, String growthNotes) {
        db.collection(db_collection_constant.BabyInfoCollection).document(babyInfoId.toString())
                .update("growthNotes", growthNotes);
    }


}
