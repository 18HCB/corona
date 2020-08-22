package com.zub.covid_19.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.zub.covid_19.api.TotalVietNam.DataTotalVietNam;
import com.zub.covid_19.api.TotalVietNam.TotalVietNamHolder;
import com.zub.covid_19.api.newDataVN.NewsDataVNFetch;
import com.zub.covid_19.api.newsDataVN.News;
import com.zub.covid_19.api.newsDataVN.NewsDataVNHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TotalVietNamRepository {

    private static TotalVietNamRepository totalVietNamRepository;

    public static TotalVietNamRepository getInstance() {
        if(totalVietNamRepository == null) {
            totalVietNamRepository = new TotalVietNamRepository();
        }
        return totalVietNamRepository;
    }

    private TotalVietNamHolder totalVietNamHolder;

    private TotalVietNamRepository() {
        totalVietNamHolder = NewsDataVNFetch.createService(TotalVietNamHolder.class);
    }

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLoading() {
        return isLoading;
    }

    public MutableLiveData<DataTotalVietNam> getTotalVietNam() {
        MutableLiveData<DataTotalVietNam> totalVietNamData = new MutableLiveData<>();
        isLoading.setValue(true);
        totalVietNamHolder.getTotalVietNam()
                .enqueue(new Callback<DataTotalVietNam>() {
                    @Override
                    public void onResponse(Call<DataTotalVietNam> call, Response<DataTotalVietNam> response) {
                        isLoading.setValue(false);


                        totalVietNamData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<DataTotalVietNam> call, Throwable t) {
                        isLoading.setValue(false);
                        Log.d("response",t.getMessage());
                        Timber.e(t);
                    }
                });

        return totalVietNamData;

    }

}
