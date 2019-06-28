package com.yt.simpleframe.http.bean;

import com.google.gson.Gson;
import com.yt.simpleframe.http.bean.entity.AdvisoryInfo;
import com.yt.simpleframe.http.bean.entity.PagingInfo;
import com.yt.simpleframe.http.bean.entity.ReFundInfo;

import java.util.List;

//还款明细
public class ReFundInfoBean extends BaseBean{



    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    private PagingInfo<ReFundInfo> DATA;

    public PagingInfo<ReFundInfo> getDATA() {
        return DATA;
    }

    public void setDATA(PagingInfo<ReFundInfo> DATA) {
        this.DATA = DATA;
    }
}
