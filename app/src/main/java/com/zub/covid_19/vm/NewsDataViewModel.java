package com.vietnam.corona.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vietnam.corona.api.newsData.NewsData;
import com.vietnam.corona.api.provData.ProvData;
import com.vietnam.corona.repo.NewsDataRepository;
import com.vietnam.corona.repo.ProvDataRepository;

public class NewsDataViewModel extends ViewModel {
    private MutableLiveData<NewsData> newsData;
    private MutableLiveData<Boolean> isLoading;
    private NewsDataRepository newsDataRepository;

    public void init() {
        if (newsData != null){
            return;
        }
        newsDataRepository = NewsDataRepository.getInstance();
        isLoading = newsDataRepository.getLoading();

    }

    public LiveData<NewsData> getNewsData() {
        return newsDataRepository.getNewsData();
    }

    public LiveData<NewsData> getNewsDataEn() {
        return newsDataRepository.getNewsDataEn();
    }

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }

}
