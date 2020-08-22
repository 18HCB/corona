
package com.zub.covid_19.api.newDataVN;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("Country_Region")
    @Expose
    private String countryRegion;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Long_")
    @Expose
    private String _long;
    @SerializedName("Confirmed")
    @Expose
    private String confirmed;
    @SerializedName("Deaths")
    @Expose
    private String deaths;
    @SerializedName("Recovered")
    @Expose
    private String recovered;
    @SerializedName("__typename")
    @Expose
    private String typename;

    public String getCountryRegion() {
        return countryRegion;
    }

    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

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
