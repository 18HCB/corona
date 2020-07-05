package com.vietnam.corona.repo;

import androidx.lifecycle.MutableLiveData;

import com.vietnam.corona.api.specData.SpecData;
import com.vietnam.corona.api.specData.SpecDataFetch;
import com.vietnam.corona.api.specData.SpecDataHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SpecDataRepository {

    private static SpecDataRepository specDataRepository;

    public static SpecDataRepository getInstance() {
        if(specDataRepository == null) {
            specDataRepository = new SpecDataRepository();
        }
        return specDataRepository;
    }

    private SpecDataHolder specDataHolder;

    private SpecDataRepository() {
        specDataHolder = SpecDataFetch.createService(SpecDataHolder.class);
    }

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLoading() {
        return isLoading;
    }

    public MutableLiveData<SpecData> getSpecData() {
        MutableLiveData<SpecData> specData = new MutableLiveData<>();
        isLoading.setValue(true);
        specDataHolder.getSpecData().enqueue(new Callback<SpecData>() {
            @Override
            public void onResponse(Call<SpecData> call, Response<SpecData> response) {
                if (response.isSuccessful()){
                    isLoading.setValue(false);
                    specData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SpecData> call, Throwable t) {
                isLoading.setValue(false);
                Timber.e(t);
            }
        });

        return specData;

    }

}
