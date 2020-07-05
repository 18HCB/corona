package com.vietnam.corona.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vietnam.corona.api.globalData.GlobalData;
import com.vietnam.corona.repo.GlobalDataRepository;

public class GlobalDataViewModel extends ViewModel {

    private MutableLiveData<GlobalData> globalDataMutableLiveData;
    private GlobalDataRepository globalDataRepository;

    public void init() {
        if (globalDataMutableLiveData != null) {
            return;
        }
        globalDataRepository = GlobalDataRepository.getInstance();
    }

    public LiveData<GlobalData> getGlobalData() {
        return globalDataRepository.getGlobalData();
    }

    public LiveData<Boolean> getLoading() {
        return globalDataRepository.getLoading();
    }

}
