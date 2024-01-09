package com.example.bellyfull.modules.visualisation.Fragments;

import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class Baby {

    String babyInfoId;
    @ServerTimestamp Date entryDate;

    Double fetalLength;
    Double fetalWeight;
    String growthNotes;
    Double headCircumference;
    String userId;

    public Baby(String babyInfoId, Date entryDate, Double fetalLength, Double fetalWeight, String growthNotes, Double headCircumference, String userId) {
        this.babyInfoId = babyInfoId;
        this.entryDate = entryDate;
        this.fetalLength = fetalLength;
        this.fetalWeight = fetalWeight;
        this.growthNotes = growthNotes;
        this.headCircumference = headCircumference;
        this.userId = userId;
    }

    public Baby(Double fetalLength, Double fetalWeight, Double headCircumference) {
        this.fetalLength = fetalLength;
        this.fetalWeight = fetalWeight;
        this.headCircumference = headCircumference;
    }

    public Baby(){}

    public String getBabyInfoId() {
        return babyInfoId;
    }

    public void setBabyInfoId(String babyInfoId) {
        this.babyInfoId = babyInfoId;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
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

    public String getGrowthNotes() {
        return growthNotes;
    }

    public void setGrowthNotes(String growthNotes) {
        this.growthNotes = growthNotes;
    }

    public Double getHeadCircumference() {
        return headCircumference;
    }

    public void setHeadCircumference(Double headCircumference) {
        this.headCircumference = headCircumference;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    // Add a default constructor

    // Add getters and setters for each field
}

/*
public class Baby {

    public Baby(long fetalLength, long fetalWeight, long headCircumference) {
        this.fetalLength = fetalLength;
        this.fetalWeight = fetalWeight;
        this.headCircumference = headCircumference;
    }

    public long getFetalLength() {
        return fetalLength;
    }

    public void setFetalLength(long fetalLength) {
        this.fetalLength = fetalLength;
    }

    public long getFetalWeight() {
        return fetalWeight;
    }

    public void setFetalWeight(long fetalWeight) {
        this.fetalWeight = fetalWeight;
    }

    public long getHeadCircumference() {
        return headCircumference;
    }

    public void setHeadCircumference(long headCircumference) {
        this.headCircumference = headCircumference;
    }

    long fetalLength, fetalWeight, headCircumference;

}
*/
