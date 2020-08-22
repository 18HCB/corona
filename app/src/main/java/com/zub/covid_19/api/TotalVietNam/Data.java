
package com.zub.covid_19.api.TotalVietNam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("totalVietNam")
    @Expose
    private TotalVietNam_ totalVietNam;

    public TotalVietNam_ getTotalVietNam() {
        return totalVietNam;
    }

    public void setTotalVietNam(TotalVietNam_ totalVietNam) {
        this.totalVietNam = totalVietNam;
    }

}
