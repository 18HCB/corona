
package com.zub.covid_19.api.TotalVietNam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalVietNam_ {

    @SerializedName("confirmed")
    @Expose
    private String confirmed;
    @SerializedName("deaths")
    @Expose
    private String deaths;
    @SerializedName("recovered")
    @Expose
    private String recovered;
    @SerializedName("__typename")
    @Expose
    private String typename;

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
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

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

}
