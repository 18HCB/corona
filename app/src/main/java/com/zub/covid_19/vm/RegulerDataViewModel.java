package com.vietnam.corona.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vietnam.corona.api.regulerData.RegulerData;
import com.vietnam.corona.repo.RegulerDataRepository;

public class RegulerDataViewModel extends ViewModel {
    private MutableLiveData<RegulerData> regulerData;
    private MutableLiveData<Boolean> isLoading;
    private RegulerDataRepository regulerDataRepository;

    public void init() {
        if (regulerData != null){
            return;
        }
        regulerDataRepository = RegulerDataRepository.getInstance();
        regulerData = regulerDataRepository.getRegulerData();
        isLoading = regulerDataRepository.getLoading();

    }

    public LiveData<RegulerData> getRegulerData() {
        return regulerData;
    }

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }

}
