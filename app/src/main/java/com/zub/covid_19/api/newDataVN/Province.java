
package com.zub.covid_19.api.newDataVN;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Province {

    @SerializedName("Province_Name")
    @Expose
    private String provinceName;
    @SerializedName("Province_Id")
    @Expose
    private String provinceId;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Long")
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
    @SerializedName("Last_Update")
    @Expose
    private Object lastUpdate;
    @SerializedName("__typename")
    @Expose
    private String typename;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
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

    public Object getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Object lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

}
