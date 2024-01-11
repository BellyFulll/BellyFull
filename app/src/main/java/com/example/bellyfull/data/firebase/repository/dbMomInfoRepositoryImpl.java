package com.example.bellyfull.data.firebase.repository;

import android.content.Context;

import com.example.bellyfull.Constant.db_collection_constant;
import com.example.bellyfull.data.firebase.collection.MomInfo;
import com.example.bellyfull.data.firebase.firebase;
import com.example.bellyfull.data.firebase.ports.dbMomInfoRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;


public class dbMomInfoRepositoryImpl implements dbMomInfoRepository {
    Context context;
    FirebaseFirestore db = firebase.getDatabase();

    public dbMomInfoRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void createMomInfo(String userId, String MomInfoId) {
        MomInfo momInfo = new MomInfo(MomInfoId, userId, new Date(), null, null, null, null, null, null, null, null, null);
        db.collection(db_collection_constant.MomInfoCollection).document(MomInfoId.toString())
                .set(momInfo);
    }

    @Override
    public void setDigestiveSymptomsCondition(String MomInfoId, String input) {
        db.collection(db_collection_constant.MomInfoCollection).document(MomInfoId.toString())
                .update("digestiveSymptomsCondition", input);
    }

    @Override
    public void setPhysicalDiscomfortsCondition(String MomInfoId, String input) {
        db.collection(db_collection_constant.MomInfoCollection).document(MomInfoId.toString())
                .update("physicalDiscomfortsCondition", input);
    }

    @Override
    public void setMentalAndEmotionalHealthCondition(String MomInfoId, String input) {
        db.collection(db_collection_constant.MomInfoCollection).document(MomInfoId.toString())
                .update("mentalAndEmotionalHealthCondition", input);
    }

    @Override
    public void setBreastAndBodyChanges(String MomInfoId, String input) {
        db.collection(db_collection_constant.MomInfoCollection).document(MomInfoId.toString())
                .update("breastAndBodyChanges", input);
    }

    @Override
    public void setUrinaryAndReproductiveHealth(String MomInfoId, String input) {
        db.collection(db_collection_constant.MomInfoCollection).document(MomInfoId.toString())
                .update("urinaryAndReproductiveHealth", input);
    }

    @Override
    public void setGastrointestinalSymptoms(String MomInfoId, String input) {
        db.collection(db_collection_constant.MomInfoCollection).document(MomInfoId.toString())
                .update("gastrointestinalSymptoms", input);
    }

    @Override
    public void setFoodDiary(String MomInfoId, String input) {
        db.collection(db_collection_constant.MomInfoCollection).document(MomInfoId.toString())
                .update("foodDiary", input);
    }

    @Override
    public void setSleepPatterns(String MomInfoId, String input) {
        db.collection(db_collection_constant.MomInfoCollection).document(MomInfoId.toString())
                .update("sleepPatterns", input);
    }

    @Override
    public void setPhotoUrl(String MomInfoId, String input) {
        db.collection(db_collection_constant.MomInfoCollection).document(MomInfoId.toString())
                .update("photoUrl", input);
    }
}
