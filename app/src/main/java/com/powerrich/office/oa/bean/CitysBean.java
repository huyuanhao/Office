package com.powerrich.office.oa.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/22
 * 版权：
 */

public class CitysBean implements Serializable{

    ArrayList<JsonBean> item1 ;
    ArrayList<ArrayList<String>> item2 ;
    ArrayList<ArrayList<ArrayList<String>>> item3 ;

    public ArrayList<JsonBean> getItem1() {
        return item1;
    }

    public void setItem1(ArrayList<JsonBean> item1) {
        this.item1 = item1;
    }

    public ArrayList<ArrayList<String>> getItem2() {
        return item2;
    }

    public void setItem2(ArrayList<ArrayList<String>> item2) {
        this.item2 = item2;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getItem3() {
        return item3;
    }

    public void setItem3(ArrayList<ArrayList<ArrayList<String>>> item3) {
        this.item3 = item3;
    }
}
