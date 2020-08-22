
package com.zub.covid_19.api.regulerData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrendlineVnCase {

    @SerializedName("__typename")
    @Expose
    private String typename;
    @SerializedName("confirmed")
    @Expose
    private String confirmed;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("deaths")
    @Expose
    private String deaths;
    @SerializedName("recovered")
    @Expose
    private String recovered;

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

}
