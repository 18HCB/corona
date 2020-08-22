package com.zub.covid_19.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zub.covid_19.api.specData.SpecDataVN;
import com.zub.covid_19.repo.SpecDataRepositoryVN;

public class SpecDataViewModelVN extends ViewModel {
    private MutableLiveData<SpecDataVN> specDataVN;
    private MutableLiveData<Boolean> isLoading;
    private SpecDataRepositoryVN specDataRepositoryVN;
    public void init(){
        if(specDataVN != null){
            return;
        }
        specDataRepositoryVN = SpecDataRepositoryVN.getInstance();
        specDataVN = specDataRepositoryVN.getSpecData();
        isLoading = specDataRepositoryVN.getLoading();
    }
    public LiveData<SpecDataVN> getSpecData(){
        return specDataVN;
    }
    public LiveData<Boolean> getLoading(){return isLoading;}
}
