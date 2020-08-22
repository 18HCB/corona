package com.zub.covid_19.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.zub.covid_19.api.ProvincesVietNam.DataProvinceVN;
import com.zub.covid_19.api.ProvincesVietNam.ProvinceVN;
import com.zub.covid_19.api.ProvincesVietNam.ProvincesVNHolder;
import com.zub.covid_19.api.newDataVN.NewsDataVNFetch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ProvinceVNRepository {

    private static ProvinceVNRepository provinceVNRepository;

    public static ProvinceVNRepository getInstance() {
        if(provinceVNRepository == null) {
            provinceVNRepository = new ProvinceVNRepository();
        }
        return provinceVNRepository;
    }

    private ProvincesVNHolder provincesVNHolder;

    private ProvinceVNRepository() {
        provincesVNHolder = NewsDataVNFetch.createService(ProvincesVNHolder.class);
    }

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLoading() {
        return isLoading;
    }




    public MutableLiveData<ProvinceVN> getProvinceVN() {
        MutableLiveData<ProvinceVN> provinceVNMutableLiveData = new MutableLiveData<ProvinceVN>();
        isLoading.setValue(true);
        provincesVNHolder.getProvinceVN()
                .enqueue(new Callback<ProvinceVN>() {
                    @Override
                    public void onResponse(Call<ProvinceVN> call, Response<ProvinceVN> response) {
                        isLoading.setValue(false);


                        provinceVNMutableLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<ProvinceVN> call, Throwable t) {
                        isLoading.setValue(false);
                        Log.d("response",t.getMessage());
                        Timber.e(t);
                    }
                });

        return provinceVNMutableLiveData;

    }

}
