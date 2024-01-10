package com.example.bellyfull.data.firebase.ports;

public interface dbMomInfoRepository {
    public void createMomInfo(String userId, String MomInfoId);
    public void setDigestiveSymptomsCondition(String MomInfoId, String input);
    public void setPhysicalDiscomfortsCondition(String MomInfoId, String input);
    public void setMentalAndEmotionalHealthCondition(String MomInfoId, String input);
    public void setBreastAndBodyChanges(String MomInfoId, String input);
    public void setUrinaryAndReproductiveHealth(String MomInfoId, String input);
    public void setGastrointestinalSymptoms(String MomInfoId, String input);
    public void setFoodDiary(String MomInfoId, String input);
    public void setSleepPatterns(String MomInfoId, String input);
    public void setPhotoUrl(String MomInfoId, String input);
}
