package com.zub.covid_19.api.newsDataVN;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface NewsDataHolder {
    /*
        @Headers({"origin: https://corona.kompa.ai",
                "content-type: application/json",
                "host: 51.79.20.199:443",
                "sec-fetch-mode: cors",
                "content-length: 228"})
         */
    @Headers({
            //"accept: */*",
            //"accept-language: en-US,en;q=0.9,vi;q=0.8",
            "Host: localhost",
            "Origin: http://localhost",

//            "Host: corona-api.kompa.ai",
//            "Content-length: 228",
//            "Access-Control-Allow-Origin: https://corona.kompa.ai",

            //"sec-fetch-mode: cors",
            //"sec-fetch-site: same-site",
            // "origin: https://corona.kompa.ai"
            //"user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36"
            //"referrer: https://corona.kompa.ai/",
            //"referrerPolicy: no-referrer-when-downgrade"
    })
    @GET("news")
    Call<News> getNews( );

}
