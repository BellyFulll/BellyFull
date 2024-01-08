package com.example.bellyfull.data.firebase.collection;

import java.util.Date;

public class MomInfo {
    private String momInfoId;
    private String userId;

    private Date entryDate;
    private String digestiveSymptomsCondition;
    private String physicalDiscomfortsCondition;
    private String mentalAndEmotionalHealthCondition;
    private String breastAndBodyChanges;
    private String urinaryAndReproductiveHealth;
    private String gastrointestinalSymptoms;
    private String foodDiary;
    private String sleepPatterns;

    public MomInfo() {
    }

    public MomInfo(String momInfoId, String userId, Date entryDate, String digestiveSymptomsCondition, String physicalDiscomfortsCondition, String mentalAndEmotionalHealthCondition, String breastAndBodyChanges, String urinaryAndReproductiveHealth, String gastrointestinalSymptoms, String foodDiary, String sleepPatterns) {
        this.momInfoId = momInfoId;
        this.userId = userId;
        this.entryDate = entryDate;
        this.digestiveSymptomsCondition = digestiveSymptomsCondition;
        this.physicalDiscomfortsCondition = physicalDiscomfortsCondition;
        this.mentalAndEmotionalHealthCondition = mentalAndEmotionalHealthCondition;
        this.breastAndBodyChanges = breastAndBodyChanges;
        this.urinaryAndReproductiveHealth = urinaryAndReproductiveHealth;
        this.gastrointestinalSymptoms = gastrointestinalSymptoms;
        this.foodDiary = foodDiary;
        this.sleepPatterns = sleepPatterns;
    }

    public String getMomInfoId() {
        return momInfoId;
    }

    public void setMomInfoId(String momInfoId) {
        this.momInfoId = momInfoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getDigestiveSymptomsCondition() {
        return digestiveSymptomsCondition;
    }

    public void setDigestiveSymptomsCondition(String digestiveSymptomsCondition) {
        this.digestiveSymptomsCondition = digestiveSymptomsCondition;
    }

    public String getPhysicalDiscomfortsCondition() {
        return physicalDiscomfortsCondition;
    }

    public void setPhysicalDiscomfortsCondition(String physicalDiscomfortsCondition) {
        this.physicalDiscomfortsCondition = physicalDiscomfortsCondition;
    }

    public String getMentalAndEmotionalHealthCondition() {
        return mentalAndEmotionalHealthCondition;
    }

    public void setMentalAndEmotionalHealthCondition(String mentalAndEmotionalHealthCondition) {
        this.mentalAndEmotionalHealthCondition = mentalAndEmotionalHealthCondition;
    }

    public String getBreastAndBodyChanges() {
        return breastAndBodyChanges;
    }

    public void setBreastAndBodyChanges(String breastAndBodyChanges) {
        this.breastAndBodyChanges = breastAndBodyChanges;
    }

    public String getUrinaryAndReproductiveHealth() {
        return urinaryAndReproductiveHealth;
    }

    public void setUrinaryAndReproductiveHealth(String urinaryAndReproductiveHealth) {
        this.urinaryAndReproductiveHealth = urinaryAndReproductiveHealth;
    }

    public String getGastrointestinalSymptoms() {
        return gastrointestinalSymptoms;
    }

    public void setGastrointestinalSymptoms(String gastrointestinalSymptoms) {
        this.gastrointestinalSymptoms = gastrointestinalSymptoms;
    }

    public String getFoodDiary() {
        return foodDiary;
    }

    public void setFoodDiary(String foodDiary) {
        this.foodDiary = foodDiary;
    }

    public String getSleepPatterns() {
        return sleepPatterns;
    }

    public void setSleepPatterns(String sleepPatterns) {
        this.sleepPatterns = sleepPatterns;
    }
}
