package com.zub.covid_19.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zub.covid_19.api.TotalVietNam.DataTotalVietNam;
import com.zub.covid_19.api.regulerData.RegulerData;
import com.zub.covid_19.repo.RegulerDataRepository;
import com.zub.covid_19.repo.TotalVietNamRepository;

public class TotalVietNamViewModel extends ViewModel {

    private MutableLiveData<DataTotalVietNam> totalDataVietNam;
    private MutableLiveData<Boolean> isLoading;
    private TotalVietNamRepository totalVietNamRepository;

    public void init() {
        if (totalDataVietNam != null){
            return;
        }
        totalVietNamRepository = TotalVietNamRepository.getInstance();
        totalDataVietNam = totalVietNamRepository.getTotalVietNam();
        isLoading = totalVietNamRepository.getLoading();

    }

    public LiveData<DataTotalVietNam> getTotalVietNam() {
        return totalDataVietNam;
    }

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }
}
