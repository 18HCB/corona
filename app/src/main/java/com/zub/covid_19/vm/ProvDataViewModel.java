package com.zub.covid_19.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zub.covid_19.api.provData.ProvData;
import com.zub.covid_19.repo.ProvDataRepository;

public class ProvDataViewModel extends ViewModel {
    private MutableLiveData<ProvData> provData;
    private MutableLiveData<Boolean> isLoading;
    private ProvDataRepository provDataRepository;

    public void init() {
        if (provData != null){
            return;
        }
        provDataRepository = ProvDataRepository.getInstance();
        provData = provDataRepository.getProvData();
        isLoading = provDataRepository.getLoading();

    }

    public LiveData<ProvData> getRegulerData() {
        return provData;
    }

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }

}
