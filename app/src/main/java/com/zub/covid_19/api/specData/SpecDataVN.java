
package com.zub.covid_19.api.specData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecDataVN {

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
