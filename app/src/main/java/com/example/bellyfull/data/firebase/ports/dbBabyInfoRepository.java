package com.example.bellyfull.data.firebase.ports;

import android.content.Context;

public interface dbBabyInfoRepository {
    public void getBabyInfo(String userId, dbBabyInfoCallback callback);

    public void setBabyInfoFetalLength(String babyInfoId, Double fetalLength);

    public void setBabyInfoFetalWeight(String babyInfoId, Double fetalWeight);

    public void setBabyInfoHeadCircumference(String babyInfoId, Double headCircumference);

    public void setBabyInfoGrowthNotes(String babyInfoId, String growthNotes);
}
