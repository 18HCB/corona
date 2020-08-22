
package com.zub.covid_19.api.regulerData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegulerDataVN {

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
