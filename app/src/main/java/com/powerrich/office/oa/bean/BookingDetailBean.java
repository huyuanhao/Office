package com.powerrich.office.oa.bean;

import java.io.Serializable;

/**
 * 文 件 名：BookingDetailBean
 * 描    述：
 * 作    者：chenhao
 * 时    间：2018/6/14
 * 版    权：v1.0
 */
public class BookingDetailBean implements Serializable{

    private String order_no;
    private String order_date;
    private String zxmkzxdh;
    private String order_item;
    private String order_name;
    private String blwindow;
    private String order_state;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getZxmkzxdh() {
        return zxmkzxdh;
    }

    public void setZxmkzxdh(String zxmkzxdh) {
        this.zxmkzxdh = zxmkzxdh;
    }

    public String getOrder_item() {
        return order_item;
    }

    public void setOrder_item(String order_item) {
        this.order_item = order_item;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getBlwindow() {
        return blwindow;
    }

    public void setBlwindow(String blwindow) {
        this.blwindow = blwindow;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }
}
