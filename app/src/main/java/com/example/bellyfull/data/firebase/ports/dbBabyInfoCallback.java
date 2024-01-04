package com.example.bellyfull.data.firebase.ports;

import com.example.bellyfull.data.firebase.collection.BabyInfo;
import com.example.bellyfull.data.firebase.collection.User;

public interface dbBabyInfoCallback {
    void onSuccess(BabyInfo babyInfo);
}
