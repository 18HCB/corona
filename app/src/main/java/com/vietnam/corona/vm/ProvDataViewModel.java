package com.vietnam.corona.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vietnam.corona.api.provData.ProvData;
import com.vietnam.corona.api.regulerData.RegulerData;
import com.vietnam.corona.repo.ProvDataRepository;
import com.vietnam.corona.repo.RegulerDataRepository;

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
