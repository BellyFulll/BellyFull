package com.example.bellyfull.data.firebase.collection;

import java.util.Date;

public class HealthData {
    private String healthDataId;
    private String sleep_pattern;
    private String foodDiary;
    private String digestiveSymptoms;
    private String physicalDiscomforts;
    private String mentalAndEmotionalHealth;
    private String breastAndBodyChanges;
    private String urinaryAndReproductiveHealth;
    private String gastrointestinalSymptoms;
    private Date healthDataDate;
    private String userId;

    public HealthData() {
    }

    public HealthData(String healthDataId, String sleep_pattern, String foodDiary, String digestiveSymptoms, String physicalDiscomforts, String mentalAndEmotionalHealth, String breastAndBodyChanges, String urinaryAndReproductiveHealth, String gastrointestinalSymptoms, Date healthDataDate, String userId) {
        this.healthDataId = healthDataId;
        this.sleep_pattern = sleep_pattern;
        this.foodDiary = foodDiary;
        this.digestiveSymptoms = digestiveSymptoms;
        this.physicalDiscomforts = physicalDiscomforts;
        this.mentalAndEmotionalHealth = mentalAndEmotionalHealth;
        this.breastAndBodyChanges = breastAndBodyChanges;
        this.urinaryAndReproductiveHealth = urinaryAndReproductiveHealth;
        this.gastrointestinalSymptoms = gastrointestinalSymptoms;
        this.healthDataDate = healthDataDate;
        this.userId = userId;
    }

    public String getHealthDataId() {
        return healthDataId;
    }

    public void setHealthDataId(String healthDataId) {
        this.healthDataId = healthDataId;
    }

    public String getSleep_pattern() {
        return sleep_pattern;
    }

    public void setSleep_pattern(String sleep_pattern) {
        this.sleep_pattern = sleep_pattern;
    }

    public String getFoodDiary() {
        return foodDiary;
    }

    public void setFoodDiary(String foodDiary) {
        this.foodDiary = foodDiary;
    }

    public String getDigestiveSymptoms() {
        return digestiveSymptoms;
    }

    public void setDigestiveSymptoms(String digestiveSymptoms) {
        this.digestiveSymptoms = digestiveSymptoms;
    }

    public String getPhysicalDiscomforts() {
        return physicalDiscomforts;
    }

    public void setPhysicalDiscomforts(String physicalDiscomforts) {
        this.physicalDiscomforts = physicalDiscomforts;
    }

    public String getMentalAndEmotionalHealth() {
        return mentalAndEmotionalHealth;
    }

    public void setMentalAndEmotionalHealth(String mentalAndEmotionalHealth) {
        this.mentalAndEmotionalHealth = mentalAndEmotionalHealth;
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

    public Date getHealthDataDate() {
        return healthDataDate;
    }

    public void setHealthDataDate(Date healthDataDate) {
        this.healthDataDate = healthDataDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
