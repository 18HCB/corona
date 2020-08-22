package com.zub.covid_19.api.specData;

public class SumMapVNProvinces {

    int nam,nu,type_1,type_2,type_3,type_4;

    public SumMapVNProvinces() {

    }

    public SumMapVNProvinces(int nam, int nu, int type_1, int type_2, int type_3, int type_4) {
        this.nam = nam;
        this.nu = nu;
        this.type_1 = type_1;
        this.type_2 = type_2;
        this.type_3 = type_3;
        this.type_4 = type_4;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public int getNu() {
        return nu;
    }

    public void setNu(int nu) {
        this.nu = nu;
    }

    public int getType_1() {
        return type_1;
    }

    public void setType_1(int type_1) {
        this.type_1 = type_1;
    }

    public int getType_2() {
        return type_2;
    }

    public void setType_2(int type_2) {
        this.type_2 = type_2;
    }

    public int getType_3() {
        return type_3;
    }

    public void setType_3(int type_3) {
        this.type_3 = type_3;
    }

    public int getType_4() {
        return type_4;
    }

    public void setType_4(int type_4) {
        this.type_4 = type_4;
    }
}
