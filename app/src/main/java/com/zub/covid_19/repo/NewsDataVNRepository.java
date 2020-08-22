package com.zub.covid_19.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.zub.covid_19.api.newDataVN.NewsDataVN;
import com.zub.covid_19.api.newsDataVN.News;
import com.zub.covid_19.api.newDataVN.NewsDataVNFetch;
import com.zub.covid_19.api.newsDataVN.NewsDataVNHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class NewsDataVNRepository {
    private static NewsDataVNRepository newsDataVNRepository;

    public static NewsDataVNRepository getInstance() {
        if(newsDataVNRepository == null) {
            newsDataVNRepository = new NewsDataVNRepository();
        }
        return newsDataVNRepository;
    }

    private NewsDataVNHolder newsDataVNHolder;

    private NewsDataVNRepository() {
        newsDataVNHolder = NewsDataVNFetch.createService(NewsDataVNHolder.class);
    }

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLoading() {
        return isLoading;
    }

    public MutableLiveData<News> getNewsData() {
        MutableLiveData<News> NewsData = new MutableLiveData<>();
        isLoading.setValue(true);
        newsDataVNHolder.getNews()
                .enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        isLoading.setValue(false);


                        NewsData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {
                        isLoading.setValue(false);
                        Log.d("response",t.getMessage());
                        Timber.e(t);
                    }
                });

        return NewsData;

    }
}
