package com.yt.simpleframe.http.bean;

import com.google.gson.Gson;
import com.yt.simpleframe.http.bean.entity.PagingInfo;
import com.yt.simpleframe.http.bean.entity.ReFundHKJHInfo;
import com.yt.simpleframe.http.bean.entity.ReFundInfo;

//还款计划
public class ReFundHKJHInfoBean extends BaseBean{

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    private PagingInfo<ReFundHKJHInfo> DATA;

    public PagingInfo<ReFundHKJHInfo> getDATA() {
        if(DATA==null){
            DATA=new PagingInfo<>();
        }
        return DATA;
    }

    public void setDATA(PagingInfo<ReFundHKJHInfo> DATA) {
        this.DATA = DATA;
    }
}
