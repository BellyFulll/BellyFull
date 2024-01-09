package com.example.bellyfull.modules.PregnancyTracking.Fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//Trial
public class MomViewModel extends ViewModel {
    private final MutableLiveData<String> momData = new MutableLiveData<>();

    public void setMomData(String data) {
        momData.setValue(data);
    }

    public LiveData<String> getMomData() {
        return momData;
    }
}
