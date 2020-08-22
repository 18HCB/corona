package com.zub.covid_19.repo;

import androidx.lifecycle.MutableLiveData;

import com.zub.covid_19.api.specData.SpecDataFetchVN;
import com.zub.covid_19.api.specData.SpecDataHolderVN;
import com.zub.covid_19.api.specData.SpecDataVN;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecDataRepositoryVN {
    private static SpecDataRepositoryVN specDataRepositoryVN;
    public static SpecDataRepositoryVN getInstance(){
        if(specDataRepositoryVN == null){
            specDataRepositoryVN = new SpecDataRepositoryVN();
        }
        return specDataRepositoryVN;
    }
    private SpecDataHolderVN specDataHolderVN;
    private SpecDataRepositoryVN(){
        specDataHolderVN = SpecDataFetchVN.createService(SpecDataHolderVN.class);
    }
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Boolean> getLoading() {return isLoading;}
    public MutableLiveData<SpecDataVN> getSpecData(){
        MutableLiveData<SpecDataVN> specDataVN = new MutableLiveData<>();
        isLoading.setValue(true);
        specDataHolderVN.getSpecData().enqueue(new Callback<SpecDataVN>() {
            @Override
            public void onResponse(Call<SpecDataVN> call, Response<SpecDataVN> response) {
                if(response.isSuccessful()){
                    isLoading.setValue(false);
                    specDataVN.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SpecDataVN> call, Throwable t) {

            }
        });
        return specDataVN;
    }
}
