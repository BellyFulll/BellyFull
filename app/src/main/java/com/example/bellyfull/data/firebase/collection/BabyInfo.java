package com.example.bellyfull.data.firebase.collection;

public class BabyInfo {
    private String babyInfoId;
    private Double fetalLength;
    private Double fetalWeight;
    private Double headCircumference;
    private String growthNotes;
    private String userId;

    public BabyInfo() {
    }

    public BabyInfo(String babyInfoId, Double fetalLength, Double fetalWeight, Double headCircumference, String growthNotes, String userId) {
        this.babyInfoId = babyInfoId;
        this.fetalLength = fetalLength;
        this.fetalWeight = fetalWeight;
        this.headCircumference = headCircumference;
        this.growthNotes = growthNotes;
        this.userId = userId;
    }

    public String getBabyInfoId() {
        return babyInfoId;
    }

    public void setBabyInfoId(String babyInfoId) {
        this.babyInfoId = babyInfoId;
    }

    public Double getFetalLength() {
        return fetalLength;
    }

    public void setFetalLength(Double fetalLength) {
        this.fetalLength = fetalLength;
    }

    public Double getFetalWeight() {
        return fetalWeight;
    }

    public void setFetalWeight(Double fetalWeight) {
        this.fetalWeight = fetalWeight;
    }

    public Double getHeadCircumference() {
        return headCircumference;
    }

    public void setHeadCircumference(Double headCircumference) {
        this.headCircumference = headCircumference;
    }

    public String getGrowthNotes() {
        return growthNotes;
    }

    public void setGrowthNotes(String growthNotes) {
        this.growthNotes = growthNotes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
