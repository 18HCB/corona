
package com.zub.covid_19.api.regulerData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("trendlineVnCases")
    @Expose
    private List<TrendlineVnCase> trendlineVnCases = null;

    public List<TrendlineVnCase> getTrendlineVnCases() {
        return trendlineVnCases;
    }

    public void setTrendlineVnCases(List<TrendlineVnCase> trendlineVnCases) {
        this.trendlineVnCases = trendlineVnCases;
    }

}
