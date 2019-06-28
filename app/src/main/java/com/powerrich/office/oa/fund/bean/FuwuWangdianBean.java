package com.powerrich.office.oa.fund.bean;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2019/3/1
 * 版权：
 */
public class FuwuWangdianBean implements Serializable {
    private String name;
    private String address;

    public FuwuWangdianBean(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
