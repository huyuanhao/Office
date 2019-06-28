package com.powerrich.office.oa.bean;

/**
 * Created by dir_wang on 2017/12/6.
 */

public class FilterClass {

    /** 过滤activity的全名*/
    public String filterClassName;
    /** 不是强制登录时，提示信息*/
    public int tipContent;
    /** 是否强制登录*/
    public boolean isForceLogin;

    public FilterClass(String name, boolean isForce, int tips) {
        this.filterClassName = name;
        this.isForceLogin = isForce;
        this.tipContent = tips;
    }
}
