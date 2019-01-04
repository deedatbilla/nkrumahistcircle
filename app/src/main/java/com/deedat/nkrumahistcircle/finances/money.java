package com.deedat.nkrumahistcircle.finances;

import java.nio.file.AccessMode;

public class money {

    private int micon;
    private String mtype;
    private String mdate;
    private double mamount;
public  money(){

}
    public money(int icon,String type ,String date,double amount) {

        this.micon=icon;
        this.mtype = type;
        this.mdate=date;
        this.mamount=amount;
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
