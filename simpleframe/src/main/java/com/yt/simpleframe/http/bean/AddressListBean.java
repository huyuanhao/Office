package com.yt.simpleframe.http.bean;

import com.yt.simpleframe.http.bean.entity.AddressInfo;

import java.util.ArrayList;

/**
 * Created by fanliang on 18/6/9.
 */

public class AddressListBean extends BaseBean{
    private ArrayList<AddressInfo> DATA;

    public ArrayList<AddressInfo> getDATA() {
        return DATA;
    }
    public void setDATA(ArrayList<AddressInfo> DATA) {
        this.DATA = DATA;
    }
}
