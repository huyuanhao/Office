package com.yt.simpleframe.http.bean;

import com.yt.simpleframe.http.bean.entity.AdvisoryInfo;
import com.yt.simpleframe.http.bean.entity.PagingInfo;

/**
 * Created by fanliang on 18/6/9.
 */

public class AdvisoryListBean extends BaseBean{
    private PagingInfo<AdvisoryInfo> DATA;

    public PagingInfo<AdvisoryInfo> getDATA() {
        return DATA;
    }

    public void setDATA(PagingInfo<AdvisoryInfo> DATA) {
        this.DATA = DATA;
    }
}
