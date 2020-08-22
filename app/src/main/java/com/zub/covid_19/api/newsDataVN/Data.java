
package com.zub.covid_19.api.newsDataVN;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("topTrueNews")
    @Expose
    private List<TopTrueNews> topTrueNews = null;

    public List<TopTrueNews> getTopTrueNews() {
        return topTrueNews;
    }

    public void setTopTrueNews(List<TopTrueNews> topTrueNews) {
        this.topTrueNews = topTrueNews;
    }

}
