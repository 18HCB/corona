
package com.zub.covid_19.api.ProvincesVietNam;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataProvinceVN {

    @SerializedName("provinces")
    @Expose
    private  List<Province> provinces = new ArrayList();
    @SerializedName("totalVietNam")
    @Expose
    private TotalVietNam totalVietNam;

    public  List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public TotalVietNam getTotalVietNam() {
        return totalVietNam;
    }

    public void setTotalVietNam(TotalVietNam totalVietNam) {
        this.totalVietNam = totalVietNam;
    }

}
