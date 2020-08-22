
package com.zub.covid_19.api.ProvincesVietNam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProvinceVN {

    @SerializedName("data")
    @Expose
    private DataProvinceVN data;

    public DataProvinceVN getData() {
        return data;
    }

    public void setData(DataProvinceVN data) {
        this.data = data;
    }

}
