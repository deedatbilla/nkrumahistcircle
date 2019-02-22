package com.deedat.nkrumahistcircle.model;

import java.nio.file.AccessMode;

public class money {

    private int micon;
    private String mtype;
    private String mcategory;
    private String mdate;
    private double mamount;
public  money(){

}
    public money(int icon,String type ,String date,double amount, String category) {

        this.micon=icon;
        this.mtype = type;
        this.mdate=date;
        this.mamount=amount;
        this.mcategory=category;
    }

    public String getMcategory() {
        return mcategory;
    }

    public String getMtype(){
        return mtype;
    }

    public int getMicon() {
        return micon;
    }

    public String getMdate() {
        return mdate;
    }

    public double getMamount() {
        return mamount;
    }
}
