package com.powerrich.office.oa.bean;

import com.google.gson.Gson;

/**
 * @author AlienChao
 * @date 2019/03/05 15:21
 */
public class LoanBean {


    /**
     * bljd : 材料录入
     * blsj : 2018-05-07
     * bz : 1
     */

    private String bljd;
    private String blsj;
    private String bz;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getBljd() {
        return bljd;
    }

    public void setBljd(String bljd) {
        this.bljd = bljd;
    }

    public String getBlsj() {
        return blsj;
    }

    public void setBlsj(String blsj) {
        this.blsj = blsj;
    }

    public String getBz() {
        if(bz.equals("1")){
            return  "已完成";
        }
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
