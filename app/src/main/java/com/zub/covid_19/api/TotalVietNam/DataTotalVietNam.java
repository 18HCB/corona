
package com.zub.covid_19.api.TotalVietNam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataTotalVietNam {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
