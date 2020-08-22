package com.zub.covid_19.vm;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zub.covid_19.api.newsData.NewsData;
import com.zub.covid_19.api.newsDataVN.News;
import com.zub.covid_19.api.provData.ProvData;
import com.zub.covid_19.repo.NewsDataRepository;
import com.zub.covid_19.repo.NewsDataVNRepository;
import com.zub.covid_19.repo.ProvDataRepository;

import org.json.JSONObject;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewsDataViewModel extends ViewModel {
    private MutableLiveData<NewsData> newsData;
    private MutableLiveData<Boolean> isLoading;
    private NewsDataRepository newsDataRepository;
    private NewsDataVNRepository newsDataVNRepository;

    public void init() {
        if (newsData != null){
            return;
        }
        newsDataRepository = NewsDataRepository.getInstance();
        newsDataVNRepository = NewsDataVNRepository.getInstance();
        isLoading = newsDataRepository.getLoading();

    }

    public LiveData<NewsData> getNewsData() {
        //Log.d("response","hiiiiii");
        newsDataVNRepository.getNewsData();
        return newsDataRepository.getNewsData();
    }

    public LiveData<News> getNewsDataEn()
    {
        return newsDataVNRepository.getNewsData();
    }

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }

}
