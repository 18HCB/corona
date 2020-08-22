package com.zub.covid_19.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zub.covid_19.api.ProvincesVietNam.DataProvinceVN;
import com.zub.covid_19.api.ProvincesVietNam.ProvinceVN;
import com.zub.covid_19.api.provData.ProvData;
import com.zub.covid_19.repo.ProvDataRepository;
import com.zub.covid_19.repo.ProvinceVNRepository;

public class ProvDataVNViewModel extends ViewModel {
    private MutableLiveData<ProvinceVN> provData;
    private MutableLiveData<Boolean> isLoading;
    private ProvinceVNRepository provinceVNRepository;

    public void init() {
        if (provData != null){
            return;
        }
        provinceVNRepository = ProvinceVNRepository.getInstance();
        provData =  provinceVNRepository.getProvinceVN();
        isLoading = provinceVNRepository.getLoading();

    }

    public LiveData<ProvinceVN> getRegulerData() {
        return provData;
    }

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }

}
