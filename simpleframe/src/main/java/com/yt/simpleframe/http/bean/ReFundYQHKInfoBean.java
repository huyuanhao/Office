package com.yt.simpleframe.http.bean;

import com.google.gson.Gson;
import com.yt.simpleframe.http.bean.entity.PagingInfo;
import com.yt.simpleframe.http.bean.entity.ReFundYQCXInfo;

//还款计划
public class ReFundYQHKInfoBean extends BaseBean{

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    private PagingInfo<ReFundYQCXInfo> DATA;

    public PagingInfo<ReFundYQCXInfo> getDATA() {
        return DATA;
    }

    public void setDATA(PagingInfo<ReFundYQCXInfo> DATA) {
        this.DATA = DATA;
    }
}
