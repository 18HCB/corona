
package com.zub.covid_19.api.specData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("vnPatientCases")
    @Expose
    private List<VnPatientCase> vnPatientCases = null;

    public List<VnPatientCase> getVnPatientCases() {
        return vnPatientCases;
    }

    public void setVnPatientCases(List<VnPatientCase> vnPatientCases) {
        this.vnPatientCases = vnPatientCases;
    }

}
