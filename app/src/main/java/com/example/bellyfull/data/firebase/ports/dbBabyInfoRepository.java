package com.example.bellyfull.data.firebase.ports;

import android.content.Context;

import java.util.UUID;

public interface dbBabyInfoRepository {
    public void createBabyInfo(String userId, String babyInfoId);

    public void setBabyInfoFetalLength(String babyInfoId, Double fetalLength);

    public void setBabyInfoFetalWeight(String babyInfoId, Double fetalWeight);

    public void setBabyInfoHeadCircumference(String babyInfoId, Double headCircumference);

    public void setBabyInfoGrowthNotes(String babyInfoId, String growthNotes);
}
