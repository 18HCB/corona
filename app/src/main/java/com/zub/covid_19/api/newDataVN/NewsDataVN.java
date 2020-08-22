
package com.zub.covid_19.api.newDataVN;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsDataVN {

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
